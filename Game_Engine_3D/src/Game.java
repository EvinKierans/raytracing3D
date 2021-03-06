import Maps.TwoRoomMap;
import Maps.longMap;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int WIDTH = (int) screenSize.getWidth(), HEIGHT = (int) screenSize.getHeight();
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public ArrayList<Texture> textures;
    public Camera camera;
    public Screen screen;
    private int fps;

    public Game() {
        thread = new Thread(this);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<Texture>();
        textures.add(Texture.wood);
        textures.add(Texture.brick);
        textures.add(Texture.bluestone);
        textures.add(Texture.stone);
        camera = new Camera(1.5, 1.5, 1, 0, 0, -0.66);
        //Change map here
        screen = new Screen(TwoRoomMap.map, TwoRoomMap.mapWidth, TwoRoomMap.mapHeight, textures, WIDTH, HEIGHT);
        addKeyListener(camera);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("What am I doing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
        start();
    }
    private synchronized void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        //Drawing the screen
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

        //Drawing crosshair
        g.setFont(new Font("Verdanna", 1, 32));
        g.setColor(Color.WHITE);    //crosshair colour
        g.drawString("+", getWidth()/2, getHeight()/2);

        //FPS counter
        g.setFont(new Font("Verdanna", 1, 16));
        g.setColor(Color.GREEN);    //FPS counter colour
        g.drawString( fps+" FPS", 10,40);

        //Showing the bufferStrategy
        bs.show();
    }
    public void run() {
        long lastTime = System.nanoTime();
        double tickrate = 100.0;
        final double ns = 1000000000.0 / tickrate;//100 times per second
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            requestFocus();
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;
            while (delta >= 1)//Make sure update is only happening 100 times a second
            {
                //handles all of the logic restricted time
                tick();
                delta--;
            }
            if(running == true) {
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS = " + frames);
                fps = frames;
                frames = 0;	//comment this out if you want 'total frames rendered'
            }
        }
    }

    public void tick() {
        screen.CenterMouse();
        screen.update(camera, pixels);
        camera.update(longMap.map);

    }

    public static void main(String [] args) {
        Game game = new Game();
    }
}