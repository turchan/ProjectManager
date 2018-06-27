package management.controller.interfaces;

public interface SecurityService
{
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
