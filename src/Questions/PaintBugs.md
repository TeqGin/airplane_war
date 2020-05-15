## the bugs I meet when I control the position of the items
>Q1:How to put the items exactly<br>
>A1:Now I can only use null-layout and use the function `setBounds` to control the position of the items<br>

>Q2:Why after i user the null-layout in the Panel but the items also appear in the unexpected position<br>
>A2:You must make all the father containers are null-layout<br>

>Q3:I make all the father contains null-layout and I can't see the button any more<br>
>A3:it may be other panels too big and cover the button<br>

>Q4:How to add picture as the frame's background
>A4:First,you should load a picture to ImageIcon,and second initialize a Label by ues the ImageIcon,and set it's size ,after that,
>add it into the container by `this.getLayeredPane().add(imageLabel,new Integer(Integer.MIN_VALUE));`,the second parameter is used to
>make sure the label always be the last component,and the last step is make the container un-opaque;<br>

```
        //load a picture
        ImageIcon ico=new ImageIcon("image/map/bg_sky.jpg");
        // initialize a label by ImageIcon
        JLabel imageLabel=new JLabel(ico);
        int weight=ico.getIconWidth();
        int height=ico.getIconHeight();
        imageLabel.setBounds(0,0,weight,height);

        this.getLayeredPane().add(imageLabel,new Integer(Integer.MIN_VALUE));

        this.setBounds(450,100,weight,height);
        //only the JPanel have the function 'setOpaque',so transform the Container to JPanel
        JPanel container=(JPanel) this.getContentPane();
        //使容器透明
        container.setOpaque(false);
```

>Q5:I meet a bug which appear more and more frames<br>
>A5_1:The reason is that i set the items static ,and they bind to the different frame,every time i click the 
>button,it will appear more and more frame<br>
>A5_2:这是因为我一开始把文本框和按钮设置为static（我已经忘记为什么要这么做了），然后因为这些数据是静态的，但我每次触发
>点击事件时，都会创建一个新的frame，由于数据是静态的，静态的button会绑定多个frame，从而造成触发点击事件以后会创造越来越多的frame。
>（其实这点我不是太明白为什么，因为我后来又限制了每个button的监听器的数量，但还是没用。）
 