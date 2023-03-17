package user;

//@SpringBootTest
//public class UserTest {
//    @Autowired
//    private UserService userService;

// 회원가입
//    public UserDTO newUser(){
//
//        UserDTO user = new UserDTO("테스트용 이메일", "테스트용 비밀번호","테스트용이름",99,"테스트용 전화번호");
//       // UserDTO user = new UserDTO("테스트용 이메일", "테스트용 비밀번호","테스트용이름","테스트용 전화번호");
//
//        return user;
//    }

    //출력
//    public UserDTO newUser(int i) {

//    UserDTO user =
//            new UserDTO("아이디"+i,"이름","비밀번호","전화번호","이메일","1991-01-01","루트","1","롤","99","01-01-01","상태");
//
//    // UserDTO user = new UserDTO("테스트용 이메일", "테스트용 비밀번호","테스트용이름","테스트용 전화번호");
//
//    return user;
//}
////    @Test
//    @Transactional
//    @Rollback(value = true)
//    @DisplayName("회원가입테스트")
//    public void userSaveTest(){
//        //  UserDTO user = new UserDTO("테스트용 이메일", "테스트용 비밀번호","테스트용이름",99,"테스트용 전화번호")
//        //  Long saveId = userService.save(member);
//       String saveId = userService.save(newUser(1));
//       UserDTO userDTO = userService.findById(saveId);
//       assertThat(newUser(1).getId()).isEqualTo(userDTO.getId());
//
//    }

//    @Test
//    @Transactional
//    @Rollback(value = true)
//    @DisplayName("로그인테스트")
//    public void loginTest(){
//        final String userEmail ="로그인용이메일";
//        final String userPassword ="로그인용비밀번호";
//        String userName ="로그인용이름";
//        int userAge =99;
//        String userMobile ="로그인용전화번호";
//
//        UserDTO userDTO = new UserDTO();
//        String saveId = userService.save(userDTO);
//        // 로그인 객체 생성 후 로그인
//        UserDTO loginUserDTO = new UserDTO();
//        loginUserDTO.setId(id);
//        loginUserDTO.setPassword(userPassword);
//        UserDTO loginResult = userService.login(loginUserDTO);
//        //로그인 결과가 not null이면 테스트 통과
//        assertThat(loginResult).isNotNull();
//    }

//    @Test
//    @DisplayName("회원데이터 저장")
//    public void userSave(){
//        IntStream.rangeClosed(1,20).forEach(i ->{
//            userService.save(newUser(i));
//        });
//    }
////
//    @Test
//    @Transactional
//    @Rollback(value = true)
//    @DisplayName("회원 삭제 테스트")
//    public void userDeleteTest(){
//        /*
//        *신규회원등록
//        * 삭제처리
//        * 해당 회원으로 조회시 null이면 통과
//         */
//        String saveId = userService.save(newUser(999));
//        userService.delete(saveId);
//        assertThat(userService.findById(saveId)).isNull();
//    }
//    }
//}