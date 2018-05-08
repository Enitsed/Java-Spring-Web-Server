package kr.co.crewmate.api.constants;

/**
 * 
 * @author 정슬기
 * DATE 2018.04.02
 * 하드코딩 방지를 위한 상수 설정
 */
public interface Constants {
	/**
	 * 에러메세지 키 - JSON으로 피드백을 하는 경우
	 */
	//public final static String USER_PW_SALT_KEY = "errorMsg";
	
	/** YN값 */
	public final static String Y = "Y";
	public final static String N = "N";
	
	
	/**
	 * 사용자 인증 세션키값
	 */
	public final static String USER_AUTH_SESSION_KEY = "userAuthSessionKey";


	/**
	 * 사용자 아이디 쿠키키값
	 */
	public final static String USER_ID_COOKIE_KEY = "uid";
	
	
	/**
	 * 서버명
	 */
	public final static String SERVER_NAME = "localhost";
	
	
	/**
	 * 서버 포트 정보
	 */
	public final static String SERVER_PORT = "8081";
	
	
	/**
	 * 로그인 경로
	 */
	public final static String LOGIN_URL = "/auth/loginForm.do";

	
	/**
	 * 파일 첨부 일시 저장 경로
	 */
	public final static String UPLOAD_TEMP_PATH = "/work/files/tmp";
	
	/**
	 * 파일 첨부 실제 저장 경로
	 */
	public final static String UPLOAD_REAL_PATH = "/work/files/real";


	/**
	 * 파일 첨부 이미지 저장 경로
	 */
	public final static String UPLOAD_IMG_PATH = "/Users/crewmate/eclipse-workspace/enitsed/src/main/webapp/images";
	

	/**
	 * 인증유형
	 */
	class authType{
		public final static String LOGIN	= "LOGIN";
		public final static String LOGOUT	= "LOGOUT";
	}

	/**
	 * 컨텐츠 유형
	 */
	class contentsType{
		public final static String BBS	= "BBS";
	}


	/**
	 * 파일서비스 유형
	 */
	class fileServiceType{
		public final static String REAL		= "REAL";
		public final static String IMAGE	= "IMAGE";
	}
	
	public static String[] FILE_SERVICE_TYPE_ALLOW = new String[] {
		fileServiceType.REAL, 
		fileServiceType.IMAGE
	};


	/**
	 * 파일유형
	 */
	class fileType{
		public final static String CONTENTS	= "contents";		// 컨텐츠
		public final static String ITEM_MST	= "ITEM_MST";		// 상품대표이미지
		public final static String ITEM_SUB	= "ITEM_SUB";		// 상품상세이미지
	}


	/**
	 * 파일 경로 prefix
	 */
	class filePrefix{
		public final static String CONTENTS	= "contents";		// 컨텐츠
		public final static String ITEM		= "item";			// 상품
	}


	/**
	 * 카테고리 타입
	 */
	class ctgType{
		public final static String DISP = "DISP";		// 전시
		public final static String PLAN = "PLAN";		// 기획매장
	}

	public static String[] CTG_TYPE_ALLOW = new String[] {ctgType.DISP, ctgType.PLAN}; 


	/**
	 * 카테고리 루트 시퀀스 정보
	 */
	class ctgRootSeq{
		public final static int DISP = 10;		// 전시
		public final static int PLAN = 20;				// 기획전
	}	


	/**
	 * 상품 카테고리 매핑 유형
	 */
	class itemCtgType{
		public final static String MST = "MST";		// 마스터
		public final static String DISP = "DISP";	// 전시
	}


	/**
	 * 템플릿 타겟 유형
	 */
	class tmplTgtType{
		public final static String PC = "PC";		// PC
		public final static String MO = "MO";		// MOBILE
	}
	
}