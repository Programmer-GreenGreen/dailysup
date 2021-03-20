package project.dailysup.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.dailysup.account.domain.Account;
import project.dailysup.account.domain.AccountRepository;
import project.dailysup.account.domain.Role;
import project.dailysup.account.dto.*;
import project.dailysup.account.exception.DuplicatedAccountException;
import project.dailysup.account.exception.NotValidWithdrawRequest;
import project.dailysup.account.exception.UserNotFoundException;
import project.dailysup.jwt.JwtTokenProvider;
import project.dailysup.util.SecurityUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final ProfilePictureService profileService;

    @Transactional(readOnly = true)
    public Account findCurrentAccount(){
        return getCurrentAccount();

    }

    @Transactional(readOnly = true)
    public RetrieveAccountDto findCurrentAccountDto(){
        Account account = getCurrentAccount();

        return new RetrieveAccountDto(account);
    }

    @Transactional(readOnly = true)
    public TokenDto logIn(LogInRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getLoginId(), requestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String jwtToken = jwtTokenProvider.createToken(authentication);
        return new TokenDto(jwtToken);
    }

    public SignUpResponseDto singUp(SignUpRequestDto requestDto){

        validateDuplicationAccount(requestDto);

        Account saveAccount = Account.builder()
                .loginId(requestDto.getLoginId())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .role(Role.USER)
                .isActivated(true)
                .build();

        accountRepository.save(saveAccount);

        return SignUpResponseDto.builder()
                                .loginId(requestDto.getLoginId())
                                .nickname(requestDto.getNickname())
                                .build();
    }

    public LoginIdDto withdraw(WithdrawDto withdrawDto){
        Account currentAccount = findCurrentAccount();

        validateWithdrawRequest(withdrawDto, currentAccount);
        accountRepository.deleteByLoginId(withdrawDto.getLoginId());
        return LoginIdDto.of(withdrawDto.getLoginId());

    }

    public void changeEmail(EmailDto emailDto) {
        Account findAccount = getCurrentAccount();
        findAccount.changeEmail(emailDto.getEmail());
    }

    private Account getCurrentAccount() {
        return accountRepository.findByLoginId(SecurityUtils.getCurrentLoginId())
                .orElseThrow(UserNotFoundException::new);
    }

    public void changePassword(PasswordDto dto){
        Account findAccount = getCurrentAccount();
        findAccount.changePassword(passwordEncoder.encode(dto.getPassword()));
    }

    public void changeProfilePicture(MultipartFile profilePicture){
        Account findAccount = getCurrentAccount();

        String oldProfileUrl = findAccount.getProfilePictureUrl();
        if(StringUtils.hasText(oldProfileUrl)) {
            profileService.delete(oldProfileUrl);
        }

        String fileName = profileService.upload(profilePicture);
        findAccount.changeProfile(fileName);
    }

    @Transactional(readOnly = true)
    public byte[] getProfilePicture(){
        Account currentAccount = findCurrentAccount();

        return profileService.download(currentAccount.getProfilePictureUrl());

    }





    private void validateWithdrawRequest(WithdrawDto withdrawDto, Account currentAccount) {

        if(!withdrawDto.getLoginId().equals(currentAccount.getLoginId())
                && passwordEncoder.matches(withdrawDto.getPassword(), findCurrentAccount().getPassword())){
            throw new NotValidWithdrawRequest();
        }
    }

    private void validateDuplicationAccount(SignUpRequestDto requestDto) {
        if(accountRepository.findByLoginId(requestDto.getLoginId()).orElse(null) != null){

            throw new DuplicatedAccountException();
        }
    }


}
