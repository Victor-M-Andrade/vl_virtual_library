package br.fai.vl.web.model;

public class Account {
	private static int idUser = -1;
	private static boolean login = false;
	private static int permissionLevel = 0;

	public static int getIdUser() {
		return idUser;
	}

	public static void setIdUser(final int idUser) {
		Account.idUser = idUser;
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
