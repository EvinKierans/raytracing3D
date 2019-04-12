import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Camera implements KeyListener{
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean lookLeft, lookRight, lookUp, lookDown, forward, back, right, left, accelerate;
    public double MOVE_SPEED = .04;
    public double drive_speed = .04;
    public double acceleratedSpeed = 0.15;
    public final double ROTATION_SPEED = .025;
    public Camera(double x, double y, double xd, double yd, double xp, double yp) {
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
    }
    public void keyPressed(KeyEvent key) {
        if(key.getKeyCode() == KeyEvent.VK_LEFT)
            lookLeft = true;
        if(key.getKeyCode() == KeyEvent.VK_RIGHT)
            lookRight = true;
        if(key.getKeyCode() == KeyEvent.VK_UP)
            lookUp = true;
        if(key.getKeyCode() == KeyEvent.VK_DOWN)
            lookDown = true;
        if(key.getKeyCode() == KeyEvent.VK_W)
            forward = true;
        if(key.getKeyCode() == KeyEvent.VK_S)
            back = true;
        if(key.getKeyCode() == KeyEvent.VK_A)
            left = true;
        if(key.getKeyCode() == KeyEvent.VK_D)
            right = true;
        if(key.getKeyCode() == KeyEvent.VK_SHIFT)
            accelerate = true;
    }
    public void keyReleased(KeyEvent key) {
        if(key.getKeyCode() == KeyEvent.VK_LEFT)
            lookLeft = false;
        if(key.getKeyCode() == KeyEvent.VK_RIGHT)
            lookRight = false;
        if(key.getKeyCode() == KeyEvent.VK_UP)
            lookUp = false;
        if(key.getKeyCode() == KeyEvent.VK_DOWN)
            lookDown = false;
        if(key.getKeyCode() == KeyEvent.VK_W)
            forward = false;
        if(key.getKeyCode() == KeyEvent.VK_S)
            back = false;
        if(key.getKeyCode() == KeyEvent.VK_A)
            left = false;
        if(key.getKeyCode() == KeyEvent.VK_D)
            right = false;
        if(key.getKeyCode() == KeyEvent.VK_SHIFT)
            accelerate = false;
            MOVE_SPEED = drive_speed;
    }
    public void update(int[][] map) {
        //WASD movement
        if(forward) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos+=xDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 0)
                yPos+=yDir*MOVE_SPEED;
        }
        if(back) {
            if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos-=xDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)] == 0)
                yPos-=yDir*MOVE_SPEED;
        }
        if(right) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos+=yDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 0)
                yPos-=xDir*MOVE_SPEED;
        }
        if(left) {
            if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos-=yDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)] == 0)
                yPos+=xDir*MOVE_SPEED;
        }
        //Acceleration case
        if(accelerate && forward) {
            MOVE_SPEED = acceleratedSpeed;
        }
        //Looking around
        if(lookRight) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
            yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
        }
        if(lookLeft) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
        }
        //TODO Y axis movement
        if(lookUp) {

        }
        if(lookDown) {

        }

    }
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}