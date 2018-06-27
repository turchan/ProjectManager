package management.controller;

import management.controller.interfaces.RoleService;
import management.controller.interfaces.SecurityService;
import management.controller.interfaces.UserService;
import management.model.Message;
import management.model.Role;
import management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
public class SecurityController
{
    private UserService userService;
    private SecurityService securityService;
    private RoleService roleService;
    private MessageSource messageSource;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model uiModel)
    {
       return "Security/login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(User user, BindingResult bindingResult,
                               Model uiModel, RedirectAttributes redirectAttributes, Locale locale)
    {
        if (bindingResult.hasErrors())
        {
            uiModel.addAttribute("message", new Message("error",
                    messageSource.getMessage("user_registration_fail", new Object[]{}, locale)));

            uiModel.addAttribute("user", user);

            return "Security/Registration";
        }

        uiModel.asMap().clear();

        redirectAttributes.addFlashAttribute("message", new Message("success",
                messageSource.getMessage("user_registration_success", new Object[]{}, locale)));

        userService.save(user);

        return "redirect:/login";
    }

    @RequestMapping(value = "/registration", params = "form", method = RequestMethod.GET)
    public String registrationForm(Model uiModel)
    {
        User user = new User();

        uiModel.addAttribute("user", user);

        return "Security/Registration";
    }

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Autowired
    public void setSecurityService(SecurityService securityService) { this.securityService = securityService; }

    @Autowired
    public void setRoleService(RoleService roleService) { this.roleService = roleService; }

    @Autowired
    public void setMessageSource(MessageSource messageSource) { this.messageSource = messageSource; }
}
