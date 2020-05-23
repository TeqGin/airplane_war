package Canvas;

import Domain.BasicIcon;
import Domain.Data;
import Domain.MyMap;
import Domain.NumberIcon;
import Frame.MainFrame;
import Service.UserService;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SettlementCanvas extends Canvas {
    private MyMap settlementMap;
    private Image settlementImage;
    private BasicIcon backImage;
    private JFrame jFrame;
    private String backgroundAddress;

    private ArrayList<NumberIcon> numberIcons=new ArrayList<NumberIcon>();

    //instance double buffer 实现双缓冲
    private Image iBuffer;
    private Graphics gBuffer;

    public SettlementCanvas(JFrame jFrame,String backgroundAddress) {
        settlementMap=new MyMap("static/image/map/bg_settlement.jpg",0,0);
        settlementImage=Toolkit.getDefaultToolkit().getImage(backgroundAddress);
        backImage=new BasicIcon("static/image/icon/back_menu.png",100,550);
        this.jFrame=jFrame;
        this.backgroundAddress=backgroundAddress;
        click();

        //initialize numbers
        for (int i = 0; i < 10 ; i++) {
            NumberIcon numberIcon=new NumberIcon("static/image/number/number_"+i+".png");
            if (i==1){
                numberIcon.setHeight(numberIcons.get(0).getHeight());
                numberIcon.setWidth(numberIcons.get(0).getWidth());
            }
            numberIcon.setX(350);
            numberIcon.setY(350);
            numberIcons.add(numberIcon);
        }

        repaint();
    }

    public void paint(Graphics g){
        if (iBuffer==null){
            iBuffer=createImage(Data.width,Data.height);
            gBuffer=iBuffer.getGraphics();
        }

        g.fillRect(0,0,Data.width,Data.height);

        gBuffer.drawImage(settlementMap.getBackground(),settlementMap.getX(),settlementMap.getY(),Data.width,Data.height,this);
        gBuffer.drawImage(settlementImage,75,75,this);
        gBuffer.drawImage(backImage.getImage(),backImage.x,backImage.y,this);
        drawScore(gBuffer);

        g.drawImage(iBuffer,0,0,null);

    }
    //draw score
    private void drawScore(Graphics gBuffer){
        int score=Data.score;
        int count=0;
        if (score==0){
            gBuffer.drawImage(numberIcons.get(0).getNumberImage(),numberIcons.get(0).getX()-(numberIcons.get(0).getWidth()+5)*count,numberIcons.get(0).getY(),this);
        }else {
            while (score > 0) {
                int right=score%10;
                NumberIcon numberIcon=numberIcons.get(right);
                gBuffer.drawImage(numberIcon.getNumberImage(),numberIcon.getX()-(numberIcon.getWidth()+5)*count,numberIcon.getY(),this);
                count++;
                score/=10;
            }
        }
        count=0;
        int coin=Data.score/100;
        coin*=Data.proportion;
        Data.user.setCoin(Data.user.getCoin()+coin);
        UserService.update(Data.user);

        if (coin==0){
            gBuffer.drawImage(numberIcons.get(0).getNumberImage(),numberIcons.get(0).getX()-(numberIcons.get(0).getWidth()+5)*count,numberIcons.get(0).getY()-70,this);
        }else {
            while (coin > 0) {
                int right=coin%10;
                NumberIcon numberIcon=numberIcons.get(right);
                gBuffer.drawImage(numberIcon.getNumberImage(),numberIcon.getX()-(numberIcon.getWidth()+5)*count,numberIcon.getY()-70,this);
                count++;
                coin/=10;
            }
        }

        count=0;
        int killNumber=Data.score/100;
        if (killNumber==0){
            gBuffer.drawImage(numberIcons.get(0).getNumberImage(),numberIcons.get(0).getX()-(numberIcons.get(0).getWidth()+5)*count,numberIcons.get(0).getY()-140,this);
        }else {
            while (killNumber > 0) {
                int right=killNumber%10;
                NumberIcon numberIcon=numberIcons.get(right);
                gBuffer.drawImage(numberIcon.getNumberImage(),numberIcon.getX()-(numberIcon.getWidth()+5)*count,numberIcon.getY()-140,this);
                count++;
                killNumber/=10;
            }
        }

    }



    public void click(){
        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x=e.getX();
                int y=e.getY();
                if (x>=backImage.x&&x<=backImage.x+backImage.width&&y>=backImage.y&&y<=backImage.y+backImage.height){
                    jFrame.dispose();
                    MainFrame mainFrame= new MainFrame("雷霆战机");
                }
            }
        });
    }


    //override this function to avoid screen flicker(or to achieve double buffer)
    @Override
    public void update(Graphics g) {
        //super.update(g) should't be used
        //super.update(g);
        paint(g);
    }



}
