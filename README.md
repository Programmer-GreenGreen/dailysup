
<h1>Dailysup</h1>
<h3>Alarm Service to Change Daily Supplements </h3>
<p>Clone coding and refactoring for study from scratch</p>
<p>Original Project: https://github.com/mash-up-kr/Tich-Backend</p>

<div>
    <p>it is not for a commercial purpose</p>
    <p>ignored credential(yml, jwt, aws properties)</p>

    What are used: spring boot mvc and security,
                   jwt, spring data jpa, aws s3 and deployed in ec2

Every request except sign-up and log-in needs <br/>
jwt token starting with "Bearer: " in "Authorization" header. 

</div>

<br><br>

<Strong> Api Doc </Strong>

root url : /api

<h3> Account </h3>


|url|method|desc|
|---|---|---|
| account || |
|/account|get|토큰 계정의 정보를 가져온다.|
|/account|post|회원가입을 요청한다.|
|/account|delete|회원 탈퇴를 요청한다.|
| auth || |
|/account/auth/log-in|post|JWT 토큰을 발급받는다.|
| forget| | |
|/account/forget/password|post|비밀번호 변경을 요청한다.(메일발송)|
|/account/forget/password|put|비밀번호를 재발급한다.(메일 인증 필요)|
|/account/forget/password/token|post|비밀번호 변경 토큰을 검증한다.|
| info| | |
|/account/info/email|get|이메일 정보를 요청한다.|
|/account/info/email|post|이메일을 변경한다.|
|/account/info/email|delete|이메일 정보를 삭제한다.|
|/account/info/password|post|바밀번호를 변경한다.|
|/account/info/nickname|get|닉네임 정보를 요청한다.|
|/account/info/nickname|post|닉네임을 변경한다.|
| profile| | |
|/account/profile|get|프로필 사진을 요청한다.|
|/account/profile|post|프로필 사진을 변경한다.|
|/account/profile|delete|프로필 사진을 삭제한다.|

<br/><br/>

<h3>Item</h3>


|url|method|desc|
|---|---|---|
|/items|get|아이템 목록을 가져온다.|
|/items|post|아이템을 추가한다.|
|/items/{itemId}|get|해당 Id의 아이템 정보를 가져온다.|
|/items/{itemId}|post|해당 Id의 아이템 정보를 변경한다.|
|/items/{itemId}|delete|아이템을 삭제한다.|
|/items/{itemId}/changeDay|post|해당 id의 최근 교체일을 갱신한다.|
|/items/{itemId}/picture|get|아이템의 사진을 요청한다.|
|/items/{itemId}/picture|post|아이템의 사진을 등록한다.|
|/items/{itemId}/picture|delete|아이템의 사진을 삭제한다.|

<br/><br/>
<h3>History</h3>

|url|method|desc|
|---|---|---|
|/histories|put|히스토리를 변경한다.|
|/histories/{itemId}|get|해당 ID의 아이템 교체 기록을 요청한다.|
|/histories/{historyId}|delete|아이템 교체 기록 중 해당 ID의 기록을 삭제한다.|

<br/><br/>
<h3>Device</h3>


|url|method|desc|
|---|---|---|
|/devices|get|디바이스 목록을 가져온다.|
|/devices|post|디바이스를 등록한다.|
|/devices|delete|디바이스를 삭제한다.|
|/devices/all|delete|디바이스 모두 삭제한다.|



