package br.fai.vl.web.model;

public class Account {
	private static int idUser = 0;
	private static boolean login = false;
	private static int permissionLevel = 0;
	private static String pageRequest = "";

	private String userEmail = "";
	private String userPassword = "";
	private int levelRequest = 0;

	public int getLevelRequest() {
		return levelRequest;
	}

	public void setLevelRequest(final int levelRequest) {
		this.levelRequest = levelRequest;
	}

	public static String getPageRequest() {
		return Account.pageRequest;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(final String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(final String userPassword) {
		this.userPassword = userPassword;
	}

	public static void setPageRequest(final String pageRequest) {
		Account.pageRequest = pageRequest;
	}

	public static void setIdUser(final int idUser) {
		Account.idUser = idUser;
	}

	public static int getIdUser() {
		return idUser;
	}

	public static boolean isLogin() {
		return login;
	}

	public static void setLogin(final boolean login) {
		Account.login = login;
	}

	public static int getPermissionLevel() {
		return permissionLevel;
	}

	public static void setPermissionLevel(final int permissionLevel) {
		Account.permissionLevel = permissionLevel;
	}

}
