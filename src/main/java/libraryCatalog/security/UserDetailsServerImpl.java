package libraryCatalog.security;

import libraryCatalog.models.User;
import libraryCatalog.repoInterfaces.UserManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServerImpl")
public class UserDetailsServerImpl implements UserDetailsService {
    private final UserManagerInterface userManagerInterface;

    @Autowired
    public UserDetailsServerImpl(UserManagerInterface userManagerInterface) {
        this.userManagerInterface = userManagerInterface;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userManagerInterface.findByLogin(username).orElseThrow(()->new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.fromUser(user);
    }
}
