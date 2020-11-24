package beansLab.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "shots")

public class Shot implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "SEQ_SHOT")
    @Column(name = "shot_id")
    private int id;

    @Column(name = "x")
    private double x;
    @Column(name = "y")
    private double y;
    @Column(name = "r")
    private double r;
    @Column(name = "rg")
    private boolean RG;
    @Column(name = "start_time")
    private LocalDateTime start;
    @Column(name = "script_time")
    private long scriptTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setRG(boolean RG) {
        this.RG = RG;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setScriptTime(long scriptTime) {
        this.scriptTime = scriptTime;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isRG() {
        return RG;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public long getScriptTime() {
        return scriptTime;
    }

    public Shot(){}
    public Shot(double xIn, double yIn, double rIn, boolean rgIn, LocalDateTime startIn, long scriptTimeIn){
        x = xIn;
        y = yIn;
        r = rIn;
        RG = rgIn;
        start = startIn;
        scriptTime = scriptTimeIn;
    }

    @Override
    public String toString() {
        return "X: " + x + " " +
        "Y: " + y + " " +
        "R: " + r + " " +
        "GR: " + RG + " " +
        "Start time: " + start + " " +
        "Script time: " + scriptTime;
    }
}
