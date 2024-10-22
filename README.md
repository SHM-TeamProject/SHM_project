# SHM_project
코틀린 캘린더 앱 만들기 프로젝트

##  프로젝트 소개
해당 앱은 사용자가 원하는 일자에 추가한 일정을 저장하고, 특정 일자를 클릭하면 해당 일자의 등록했던 일정을 보여주는 캘린더 앱입니다.

## 📅 만든 기간
- 2024.09.11(수) ~ ing
  
## 💻 개발 환경
- **Version** : Java 22
- **IDE** : Android Studio
- **Framework** : Android

## 앱 업데이트
<b>[2024.09.15 ver]</b><br>
- 캘린더의 기본 UI를 만들었습니다. 달력의 날짜를 선택하면 해당 월과 날짜에 맞게 앱 아래의 텍스트가 바뀝니다.

<b>[2024.09.17 ver]</b><br>
- 캘린더 앱의 '+'을 누르면 일정의 제목과 내용, 시간을 입력할 수 있는 Activity를 추가하였습니다.

<b>[2024.09.21 ver]</b><br>
- 캘린더의 일정의 제목과 내용을 DB에 추가되도록 구현하였습니다.

<b>[2024.09.28 ver]</b><br>
- 선택한 일정을 클릭하면, 해당 일자에 대한 일정의 수정과 삭제가 가능하도록 구현하였습니다.

<b>[2024.09.29 ver]</b><br>
- 원하는 일자에 대한 일정의 수정과 삭제를 할 때, DB에도 저장이 되도록 구현하였습니다.<br>
- '+'버튼을 누르고 있을 때, 사용자가 구별할 수 있도록 색상 변화를 추가하였습니다.<br>
- '+' 버튼의 왼쪽에 있는 EditText에 값을 입력한 후, '+' 버튼을 클릭하면 선택한 날짜에 입력한 값이 제목으로 설정된 일정이 추가됩니다.
  
① 버튼을 클릭하지 않은 상태<br>
<img src="https://github.com/user-attachments/assets/5e228765-1668-481e-9454-bbaf26595a78" width="100" height="100"/><br>
② 버튼을 클릭하고 있는 상태<br>
<img src="https://github.com/user-attachments/assets/e4caefbf-2586-4905-8579-8eb1cca891ef" width="100" height="100"/><br>

<b>[2024.09.30 ver]</b><br>
- '+' 버튼을 누르면 전환되는 일정 추가 Activity에서 날짜를 선택할 수 있도록 하였습니다.<br>
- 애플리케이션 실행 시 최초로 선택된 오늘 날짜에 대한 저장 내용이 DB의 다른 위치에 저장되던 현상을 수정하였습니다.

## 앱 스크린샷
① 앱 시작화면<br>
<img src=https://github.com/user-attachments/assets/3a6420f2-37e6-4d1b-b9b8-448fa7e4e775 width="300" height="400"/>

② 앱 일정 저장 화면<br>
<img src="https://github.com/user-attachments/assets/b08a2abb-4333-4a58-941f-056912235a93" width="300" height="400"/>

③ 앱 일정 수정 화면<br>
<img src="https://github.com/user-attachments/assets/5c0f08f9-ca76-4b09-9625-dc84c7fd51c1" width="300" height="400"/>
