package resources;

import beansLab.entities.Shot;
import beansLab.entities.User;
import dao.UserDao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

@Startup
@Singleton
public class UserManager {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserManager.class);

    private ReentrantLock sessionLock;
    private ReentrantLock userLock;
    private ReentrantLock userDAOLock;

    private UserDao usersDao;

    private CopyOnWriteArrayList<User> users;

    private ConcurrentHashMap<String, UserInf> goingSessions = new ConcurrentHashMap<>(); //Session id + userInf

    public UserManager() {

    }

    @PostConstruct
    public void init(){
        usersDao = new UserDao();
        users = usersDao.findAllUsers();
        users.stream().forEachOrdered(user -> {
            log.info("UserInformFor " + user.getLogin() + " ------");
        });
        log.info("Initialised user manager: " + users.size());
        userLock = new ReentrantLock();
        sessionLock = new ReentrantLock();
        userDAOLock = new ReentrantLock();
    }

    public int checkUser(String login, String password) throws Exception {
        //password is a HASH of real str of password
        userLock.lock();
        Stream<User> logined = users.stream().filter(user -> (user.getLogin().equals(login)));
        if (logined.count() == 0) {
            return 1;
        }
        long matches = users.stream().filter(user -> (user.getLogin().equals(login))).filter(user -> user.getPassword().equals(password)).count();
        if (matches == 1) {
            userLock.unlock();
            return 0;
        } else if (matches == 0) {
            userLock.unlock();
            return 2;
        } else {
            userLock.unlock();
            throw new Exception("There are identical users");
        }
    }

    public UserInf loginUser(String login, String password, HttpSession session) {
        sessionLock.lock();
        try {
            User user = getUser(login, password);
            session.setAttribute("User", user);
            UserInf userInf = new UserInf(user, session.getId());
            goingSessions.put(session.getId(), userInf);
            sessionLock.unlock();
            return userInf;
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        sessionLock.unlock();
        return null;
    }

    public void endSession(HttpSession session){
        sessionLock.lock();
        goingSessions.remove(session.getId());
        session.removeAttribute("User");
        log.info("There are " + goingSessions.size() + " sessions");
        sessionLock.unlock();
    }

    private User getUser(String login, String password) throws Exception {
        userLock.lock();
        Stream<User> logined = users.stream().filter(user -> (user.getLogin().equals(login) && user.getPassword().equals(password)));
        if (logined.count() > 0) {
            User use = users.stream().filter(user -> (user.getLogin().equals(login) && user.getPassword().equals(password))).findFirst().get();
            userLock.unlock();
            return use;
        } else {
            userLock.unlock();
            throw new Exception("Didn't find that");
        }

    }

    public boolean addUser(User user) {
        userDAOLock.lock();
        if(users.stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin()))){
            userDAOLock.unlock();
            return false;
        }else {
            users.add(user);
            usersDao.saveUser(user);
            userDAOLock.unlock();
            return true;
        }

    }

    public void addShotToUser(User user, Shot shot) {
        userDAOLock.lock();
        user.addShot(shot);
        usersDao.updateUser(user);
        userDAOLock.unlock();
    }

    public boolean hasSession(HttpSession session, Cookie tokenCookie){
        sessionLock.lock();
        boolean b = false;
        if(goingSessions.containsKey(session.getId())){
            UserInf userInf = goingSessions.get(session.getId());
            int token = userInf.getToken();
            String cookieToken = tokenCookie.getValue();
            try {
                b = token == Integer.parseInt(cookieToken);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        sessionLock.unlock();
        return b;
    }

    public void clearUser(User user){
        userDAOLock.lock();
        usersDao.deleteShots(user);
        log.info("User: " + user.getLogin() + " cleared NUM of Shots: " + user.getShots().size());
        userDAOLock.unlock();
    }

//
//    @PreDestroy
//    private void close(){
//        rLock.lock();
//        usersDao.close();
//        rLock.unlock();
//    }

}