package Canvas;

import Domain.BasicIcon;
import Domain.Bullet;
import Domain.Data;
import Domain.NumberIcon;
import Service.UserService;
import Frame.MainFrame;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ArmsStrongCanvas extends Canvas implements Runnable {
    private ArrayList<String> stringArrayList=new ArrayList<String>();
    private ArrayList<ArrayList<Bullet>> bullets=new ArrayList<ArrayList<Bullet>>();
    private BasicIcon backIcon;
    private BasicIcon buyIcon;
    private BasicIcon stoneIcon;
    private Image backgroundImage;
    private Image nextImage,beforeImage;
    private int page=1;
    private ArrayList<NumberIcon> numberIcons=new ArrayList<NumberIcon>();
    private JFrame jFrame;

    private int number=0;

    private Image iBuffer;
    private Graphics gBuffer;
    public ArmsStrongCanvas(String backgroundAddress, JFrame jFrame) {
        this.jFrame=jFrame;
        //find all user bullet types
        for (int i = 0; i < 8; i++) {
            String address="static/image/bullet/user_bullet_"+i+".png";
            stringArrayList.add(address);
        }
        //load background
        backgroundImage=Toolkit.getDefaultToolkit().getImage(backgroundAddress);
        nextImage=Toolkit.getDefaultToolkit().getImage("static/image/icon/next.png");
        beforeImage=Toolkit.getDefaultToolkit().getImage("static/image/icon/before.png");

        for (int i = 0; i < stringArrayList.size(); i++) {
            ArrayList<Bullet>bulletArrayList=new ArrayList<Bullet>();
            Bullet bullet=new Bullet(stringArrayList.get(i),200,100+(i%4)*100);
            bulletArrayList.add(bullet);
            bullets.add(bulletArrayList);
        }

        for (int i = 0; i < 10 ; i++) {
            NumberIcon numberIcon=new NumberIcon("static/image/number/number_"+i+".png");
            if (i==1){
                numberIcon.setHeight(numberIcons.get(0).getHeight());
                numberIcon.setWidth(numberIcons.get(0).getWidth());
            }
            numberIcons.add(numberIcon);
        }

        click();
        backIcon=new BasicIcon("static/image/icon/back_menu.png",100,570);
        buyIcon=new BasicIcon("static/image/icon/buy.png",300,100);
        stoneIcon=new BasicIcon("static/image/icon/stone.png",440,5);
    }

    @Override
    public void paint(Graphics g){
        if (iBuffer==null){
            iBuffer= createImage(Data.width,Data.height);
            gBuffer=iBuffer.getGraphics();
        }

        gBuffer.fillRect(0,0,Data.width,Data.height);

        gBuffer.drawImage(backgroundImage,0,0,Data.width,Data.height,this);
        //draw bullets
        for (int i = (page-1)*4; i < page*4 && i<stringArrayList.size() ; i++) {
            for (int j = 0; j < bullets.get(i).size(); j++) {
                drawBullet(gBuffer,bullets.get(i).get(j));
            }
        }
        if (page!=1){
            gBuffer.drawImage(beforeImage,0,570,this);
        }
        if (page*4<stringArrayList.size()){
            gBuffer.drawImage(nextImage,430,570,this);
        }
        drawScore(gBuffer);
        gBuffer.drawImage(backIcon.getImage(),backIcon.x,backIcon.y,this);
        gBuffer.drawImage(stoneIcon.getImage(),stoneIcon.x,stoneIcon.y,this);

        if (page*4<=stringArrayList.size()){
            number=4;
        }else {
            number=4-(page*4-stringArrayList.size());
        }
        for (int i = 0; i < number; i++) {
            gBuffer.drawImage(buyIcon.getImage(),buyIcon.x,buyIcon.y+i*100,this);
        }

        g.drawImage(iBuffer,0,0,null);
    }

    //draw score
    private void drawScore(Graphics gBuffer){
        int score=Data.user.getCoin();
        int count=0;
        if (score==0){
            gBuffer.drawImage(numberIcons.get(0).getNumberImage(),numberIcons.get(0).getX()-(numberIcons.get(0).getWidth()+5)*count,numberIcons.get(0).getY(),this);
        }else {
            while (score > 0) {
                int right=score%10;
                NumberIcon numberIcon=numberIcons.get(right);
                gBuffer.drawImage(numberIcon.getNumberImage(),numberIcon.getX()-(numberIcon.getWidth()+5)*count-70,numberIcon.getY(),this);
                count++;
                score/=10;
            }
        }
    }

    private void drawBullet(Graphics gBuffer, Bullet bullet){
        gBuffer.drawImage(bullet.getBulletImage(),bullet.getX(),bullet.getY(),this);
    }

    private void move(){
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < bullets.get(i).size(); j++) {
                if (bullets.get(i).get(j).getY() >= (i%4)*100+100){
                    bullets.get(i).get(j).move(5);
                }else {
                    bullets.get(i).remove(bullets.get(i).get(j));
                }
            }
            if (bullets.get(i).size()==0||
                    System.currentTimeMillis() - bullets.get(i).get(bullets.get(i).size()-1).getShootTime() >= 200){
                bullets.get(i).add(new Bullet(stringArrayList.get(i),200,100+(i%4)*100));
            }
        }
        repaint();
    }

    private void click(){
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x=e.getX();
                int y=e.getY();
                if (page!=1){
                    ImageIcon icon=new ImageIcon("static/image/icon/before.png");
                    if (x>=0&&x<=0+icon.getIconWidth()&&y>=570&&y<=570+icon.getIconHeight()){
                        page--;
                    }
                }
                if (page*4<stringArrayList.size()){
                    ImageIcon icon=new ImageIcon("static/image/icon/next.png");
                    if (x>=430&&x<=430+icon.getIconWidth()&&y>=570&&y<=570+icon.getIconHeight()){
                        page++;
                    }
                }
                if (x>=backIcon.x&&x<=backIcon.x+backIcon.getWidth()&&y>=backIcon.y&&y<=backIcon.y+backIcon.height){
                    jFrame.dispose();
                    MainFrame mainFrame=new MainFrame("雷霆战机");
                }
                for (int i = 0; i < number; i++) {
                    if (x>=buyIcon.x&&x<=buyIcon.x+backIcon.width&&y>=buyIcon.y+i*100&&y<=buyIcon.y+i*100+buyIcon.height){
                        if (Data.user.getCoin()<=page*500){
                            JOptionPane.showMessageDialog(null,"余额不足！需要扣除"+page*500+"晶石！","提示",JOptionPane.INFORMATION_MESSAGE);
                        }else {
                            Data.user.setCoin(Data.user.getCoin()-page*500);
                            Data.userPlaneBulletAddress=stringArrayList.get((page-1)*4+i);
                            Data.user.setUserBulletAddress(Data.userPlaneBulletAddress);

                            UserService.update(Data.user);
                            JOptionPane.showMessageDialog(null,"切换成功！","提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
    }



    @Override
    public void run() {
        while(true){
            move();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //override this function to avoid screen flicker(or to achieve double buffer)
    @Override
    public void update(Graphics g) {
        //super.update(g) should't be used
        //super.update(g);
        paint(g);
    }
}
