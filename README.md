
<h1>Dailysup</h1>
<h3>Alarm Service to Change Daily Supplements </h3>
<h5>Clone coding and refactoring for study from scratch</h5>
<h5>Original Project: https://github.com/mash-up-kr/Tich-Backend</h5>

<div>
    <p>it is not for commercial purpose</p>
    <p>ignored credential(yml, jwt, aws properties)</p>
    <p>spring mvc, spring security, jwt, spring data jpa, aws s3</p>
</div>

<h5>Account<h5/>

/api/account/

|url|method|desc|
|---|---|---|
|current|get|토큰의 계정의 정보를 반환|
|sign-up|post|회원가입 요청|
|log-in|post|토큰 발급|
|withdraw|post|회원 탈퇴|
|email|post|이메일 변경|
|password|post|비밀번호 변경|
|profile|get|프로필 사진 조회|
|profile|post|프로필 사진 변경|



<h5>Item<h5/>

/api/item/

|url|method|desc|
|---|---|---|
| |get|토큰 계정의 아이템 목록 반환|
|{id}|get|해당 id의 아이템 정보 반환|
|{itemId}|post|아이템 정보 수정|
|update/{itemId}|post|아이템 교체일 갱신|
|{id}|delete|아이템 삭제|

아이템 프로필 추가 및 수정 api 구현 예정


<h5>History<h5/>

/api/history/

|url|method|desc|
|---|---|---|
|{itemId}|get|아이템의 교체 기록 반환|
| |put|아이템 교체 기록 수정|
|{historyId}|post|교체 기록 삭제|


<h5>Device<h5/>

push 알림 device CRUD api

/api/device/

|url|method|desc|
|---|---|---|
| |get|디바이스 목록 반환|
| |post|디바이스 등록|
| |delete|디바이스 삭제(fcm 토큰이 담겨있기 때문에 body 활용)|
|/all|delete|디바이스 모두 삭제|



