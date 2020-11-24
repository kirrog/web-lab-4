package resources;

import beansLab.entities.Shot;
import beansLab.entities.User;
import services.UserService;

import javax.ejb.Singleton;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Singleton
public class UserManager {

    private UserService userService;

    private List<User> users;

    private HashMap<String, UserInf> goingSessions = new HashMap<>(); //Session id + userInf

    public UserManager() {
        userService = new UserService();
        users = userService.findAllUsers();
    }

    public int checkUser(String login, String password) throws Exception {
        //password is a HASH of real str of password
        Stream<User> logined = users.stream().filter(user -> (user.getLogin() == login));
        if (logined.count() == 0) {
            return 1;
        }
        long matches = logined.filter(user -> user.getPassword() == password).count();
        if (matches == 1) {
            return 0;
        } else if (matches == 0) {
            return 2;
        } else {
            throw new Exception("There are identical users");
        }
    }

    public UserInf loginUser(String login, String password, HttpSession session) {
        try {
            User user = getUser(login, password);
            session.setAttribute("User", user);
            UserInf userInf = new UserInf(user, session.getId());
            goingSessions.put(session.getId(), userInf);
            return userInf;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void endSession(HttpSession session){
        goingSessions.remove(session.getId());
        session.removeAttribute("User");
    }

    private User getUser(String login, String password) throws Exception {
        Stream<User> logined = users.stream().filter(user -> (user.getLogin() == login && user.getPassword() == password));
        if (logined.count() > 0) {
            return logined.findFirst().get();
        } else {
            throw new Exception("Didn't find that");
        }
    }

    private String generateHash(String login, String password) throws Exception {
        Stream<User> userStream = users.stream().filter(userr -> ((userr.getLogin() == login) && (userr.getPassword() == password)));
        if (userStream.count() > 0) {
            if (userStream.count() == 1) {
                User user = userStream.findFirst().get();
                return String.valueOf(((String.valueOf(user.getId())).hashCode()));
            } else {
                throw new Exception("There are identical users");
            }
        } else {
            throw new Exception("Doesn't have this user");
        }
    }

    public boolean addUser(User user) {
        if(users.contains(user)){
            return false;
        }else {
            users.add(user);
            userService.saveUser(user);
            return true;
        }

    }

    public void addShotToUser(User user, Shot shot) {
        user.addShot(shot);
        userService.updateUser(user);
    }

    public boolean hasSession(HttpSession session){
        return goingSessions.containsKey(session.getId());
    }

}