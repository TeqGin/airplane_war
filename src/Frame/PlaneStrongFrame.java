package Frame;

import Domain.BasicIcon;
import Domain.Data;
import Domain.NumberIcon;
import Service.UserService;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlaneStrongFrame extends JFrame implements Runnable {
    private ArrayList<BasicIcon> userPlaneIcons=new ArrayList<BasicIcon>();
    private BasicIcon backIcon;
    private BasicIcon background;
    private int page=1;
    private int number=0;
    private BasicIcon stoneIcon;

    private BasicIcon buyIcon;

    private ArrayList<NumberIcon> numberIcons=new ArrayList<NumberIcon>();


    private Image iBuffer;
    private Graphics gBuffer;

    public PlaneStrongFrame() throws HeadlessException {
        this.setTitle("选择战机");
        this.setBounds(Data.x,Data.y,Data.width,Data.height);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < 4; i++) {
            userPlaneIcons.add(new BasicIcon("static/image/plane/user_plane_"+i+".png",200,100+i*100));
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

        buyIcon=new BasicIcon("static/image/icon/buy.png",300,100);
        backIcon=new BasicIcon("static/image/icon/back_menu.png",100,570);
        stoneIcon=new BasicIcon("static/image/icon/stone.png",440,50);
        background=new BasicIcon("static/image/map/bg_settlement.jpg",0,0);
    }

    public void paint(Graphics g){
        if (iBuffer==null){
            iBuffer=createImage(Data.width,Data.height);
            gBuffer=iBuffer.getGraphics();
        }

        gBuffer.fillRect(0,0,Data.width,Data.height);
        gBuffer.drawImage(background.getImage(),background.x,background.y,Data.width,Data.height,this);
        gBuffer.drawImage(backIcon.getImage(),backIcon.x,backIcon.y,this);
        gBuffer.drawImage(stoneIcon.getImage(),stoneIcon.x,stoneIcon.y,this);
        drawScore(gBuffer);

        for (int i = (page-1)*4; i < page*4 ||i<userPlaneIcons.size(); i++) {
            gBuffer.drawImage(userPlaneIcons.get(i).getImage(),userPlaneIcons.get(i).x,userPlaneIcons.get(i).y,this);
        }

        if (page*4<=userPlaneIcons.size()){
            number=4;
        }else {
            number=4-(page*4-userPlaneIcons.size());
        }
        for (int i = 0; i < number; i++) {
            gBuffer.drawImage(buyIcon.getImage(),buyIcon.x,buyIcon.y+i*100,this);
        }

        g.drawImage(iBuffer,0,0,null);
    }

    @Override
    public void update(Graphics g){
        paint(g);
    }

    @Override
    public void run() {

    }

    private void click(){
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x=e.getX();
                int y=e.getY();

                if (x>=backIcon.x&&x<=backIcon.x+backIcon.getWidth()&&y>=backIcon.y&&y<=backIcon.y+backIcon.height){
                    PlaneStrongFrame.this.dispose();
                    MainFrame mainFrame=new MainFrame("雷霆战机");
                }
                for (int i = 0; i < number; i++) {
                    if (x>=buyIcon.x&&x<=buyIcon.x+backIcon.width&&y>=buyIcon.y+i*100&&y<=buyIcon.y+i*100+buyIcon.height){
                        if (Data.user.getCoin()<=page*500){
                            JOptionPane.showMessageDialog(null,"余额不足！需要扣除"+page*500+"晶石！","提示",JOptionPane.INFORMATION_MESSAGE);
                        }else {
                            Data.user.setCoin(Data.user.getCoin()-page*500);
                            Data.userPlaneAddress=userPlaneIcons.get((page-1)*4+i).getAddress();
                            Data.user.setUserPlaneAddress(Data.userPlaneAddress);
                            UserService.update(Data.user);
                            JOptionPane.showMessageDialog(null,"切换成功！","提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
    }
    //draw score
    private void drawScore(Graphics gBuffer){
        int score=Data.user.getCoin();
        int count=0;
        if (score==0){
            gBuffer.drawImage(numberIcons.get(0).getNumberImage(),numberIcons.get(0).getX()-(numberIcons.get(0).getWidth()+5)*count,numberIcons.get(0).getY()+40,this);
        }else {
            while (score > 0) {
                int right=score%10;
                NumberIcon numberIcon=numberIcons.get(right);
                gBuffer.drawImage(numberIcon.getNumberImage(),numberIcon.getX()-(numberIcon.getWidth()+5)*count-70,numberIcon.getY()+40,this);
                count++;
                score/=10;
            }
        }
    }
}
