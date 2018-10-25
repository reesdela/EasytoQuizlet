package webstuff;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import java.util.ArrayList;
import java.io.*;
import org.openqa.selenium.*;
import java.util.HashMap;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class WebStuff  
{
    
    //still need to add error handling and what not but i should have uploaded this a month ago
    public WebStuff(String moodleadd, String moodleuser, char[] moodlepass, String quizuser, char[] quizpass) throws InterruptedException
    {
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        HashMap<Integer, String> map = new HashMap();
        HashMap<Integer, String> answer = new HashMap();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        org.openqa.selenium.Dimension dim = new org.openqa.selenium.Dimension(1200, 790);
        driver.manage().window().setSize(dim);
        
      
        driver.get(moodleadd);
        
        // Find the text input element by its name
        WebElement element = driver.findElement(By.id("username"));

        // Enter something to search for
        element.sendKeys(moodleuser);
        
        element = driver.findElement(By.id("password"));
        
        String firstPass = String.valueOf(moodlepass);
        element.sendKeys(firstPass);
        
        element.submit();
        
        if(!(driver.getCurrentUrl().equals(moodleadd)))
        {
            System.out.println("shit");
            System.exit(0);
        }
 
        element = driver.findElement(By.xpath("//div[@class='othernav']/.."));
        element = element.findElement(By.xpath(".//div[@class='qn_buttons clearfix allquestionsononepage']"));
        String word = element.getText().substring(element.getText().length()-2, element.getText().length());
        int qNum = Integer.parseInt(word);
        for(int i = 1; i <= qNum; i++)
        {
            element = driver.findElement(By.xpath("//div[@id='q" + i + "']"));
            element = element.findElement(By.xpath(".//div[@class='rightanswer']"));
            String key = element.getText();
            answer.put(i, key);
            element = driver.findElement(By.xpath("//div[@id='q" + i + "']"));
            element = element.findElement(By.xpath(".//div[@class='qtext']"));
            String value = element.getText();
            map.put(i,value);
        }
        
        driver.get("https://quizlet.com/");
        
        Thread.sleep(1000);
        
        element = driver.findElement(By.xpath("//div[@class='SiteHeader-signIn']"));
        element = element.findElement(By.xpath(".//button[@class='UILink UILink--inverted']"));
        element.click();
        element = driver.findElement(By.xpath("//div[@class='UIModalBody']"));
        element = element.findElement(By.xpath(".//input[@name='username']"));
        element.sendKeys(quizuser);
        element = element.findElement(By.xpath("//div[@class='UIModalBody']"));
        element = element.findElement(By.xpath(".//input[@name='password']"));
        String secondPass = String.valueOf(quizpass);
        element.sendKeys(secondPass);
        element.submit();
        
        Thread.sleep(1000);
        driver.get("https://quizlet.com/create-set");
        
        Thread.sleep(1000);
        element = driver.findElement(By.className("NotificationContainer"));
        element = element.findElement(By.xpath(".//button[@class='UILink']"));
        if(element.getText().equals("Discard it"))
        {
            element.click();
            Thread.sleep(1000);
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
        
        Thread.sleep(1000);
        
        element = driver.findElement(By.tagName("textarea"));
        element.sendKeys("Placeholder");
        element = driver.findElement(By.xpath("//div[@data-term-luid='term-0']"));
        element = element.findElement(By.xpath(".//div[@class='TermContent-side TermContent-side--word']"));
        element = element.findElement(By.xpath(".//textarea[@tabindex='7']"));
        element.sendKeys("English");
        element = driver.findElement(By.xpath("//div[@data-term-luid='term-0']"));
        element = element.findElement(By.xpath(".//div[@class='TermContent-side TermContent-side--definition']"));
        element = element.findElement(By.xpath(".//textarea[@tabindex='7']"));
        element.sendKeys("English");
       
        // fill in first 5 cards
        for(int i = 1; i < 5; i++)
        {
            element = driver.findElement(By.xpath("//div[@data-term-luid='term-" + i + "']"));
            element = element.findElement(By.xpath(".//div[@class='TermContent-side TermContent-side--word']"));
            element = element.findElement(By.xpath(".//textarea[@tabindex='7']"));
            element.sendKeys(map.get(i));
            element = driver.findElement(By.xpath("//div[@data-term-luid='term-" + i + "']"));
            element = element.findElement(By.xpath(".//div[@class='TermContent-side TermContent-side--definition']"));
            element = element.findElement(By.xpath(".//textarea[@tabindex='7']"));
            element.sendKeys(answer.get(i));
        }
        
        //fill in rest while also creating new ones
        for(int i = 5; i <= qNum; i++)
        {
            element = driver.findElement(By.xpath("//div[@data-term-luid='term-" + i + "']"));
            element = element.findElement(By.xpath(".//button[@class='UILinkButton']"));
            element.click();
            element = driver.findElement(By.xpath("//div[@data-term-luid='term-" + i + "']"));
            element = element.findElement(By.xpath(".//div[@class='TermContent-side TermContent-side--word']"));
            element = element.findElement(By.xpath(".//textarea[@tabindex='7']"));
            element.sendKeys(map.get(i));
            element = driver.findElement(By.xpath("//div[@data-term-luid='term-" + i + "']"));
            element = element.findElement(By.xpath(".//div[@class='TermContent-side TermContent-side--definition']"));
            element = element.findElement(By.xpath(".//textarea[@tabindex='7']"));
            element.sendKeys(answer.get(i));
            Thread.sleep(1000);
        }
        
        element = driver.findElement(By.xpath("//div[@data-term-luid='term-0']"));
        element = element.findElement(By.xpath(".//div[@class='TermContent-side TermContent-side--word']"));
        element = element.findElement(By.xpath(".//textarea[@tabindex='7']"));
        element.sendKeys(" Language");
        Thread.sleep(500);
        

        
        element = driver.findElement(By.xpath("//div[@class='CreateSetPage-footer']"));
        element = element.findElement(By.xpath(".//button[@class='UIButton UIButton--hero']"));
        element.click();
        
        Thread.sleep(1000);
        
        driver.close();
        
        
        
        
        /*
        List<WebElement> arry = driver.findElements(By.xpath("//img[contains(@role, 'presentation')]"));
        
        ArrayList<String> src = new ArrayList<String>();
        
          for(int i = 0; i < arry.size(); i++)
        {
            src.add(arry.get(i).getAttribute("src"));
        }   
          System.out.println(arry.size());
          int width = Integer.parseInt(arry.get(0).getAttribute("width"));
          int height = Integer.parseInt(arry.get(0).getAttribute("height"));
        ((JavascriptExecutor)driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        

       //get height and width to resize after screenshot
        driver.get(src.get(0));
        WebElement okay = driver.findElement(By.tagName("img"));
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage inImage = ImageIO.read(scrFile);
        BufferedImage outImage = new BufferedImage(width, height, inImage.getType());
        Graphics2D g2d = outImage.createGraphics();
        g2d.drawImage(inImage, 0, 0, width, height, null);
        g2d.dispose();
        ImageIO.write(outImage, "png", new File("/Users/reesdelahoussaye/Documents/fuck.png"));
        */
        
    }
    private static void allocatevar(String addr, String user1, char[] pass1, String user2, char[] pass2, JFrame frame)
    {
        String moodleaddr = addr;
        String moodlesuser = user1;
        char[] moodlepass = pass1;
        String quizuser = user2;
        char[] quizpass = pass2;
        try
        {
            frame.dispose();
            WebStuff webstuff = new WebStuff(moodleaddr, moodlesuser, moodlepass, quizuser, quizpass);
        }
        catch(InterruptedException e)
        {
            
        }
    }
    
    public static void main(String[] args) throws IOException,InterruptedException
    {
        
        JFrame frame = new JFrame();
        SpringLayout spring = new SpringLayout();
        JPanel layout = new JPanel(spring);
        Container contentPane = frame.getContentPane();
        JLabel label1 = new JLabel("moodle addr:");
        JLabel label2 = new JLabel("mylsu user:");
        JLabel label3 = new JLabel("mylsu pass:");
        JLabel label4 = new JLabel("quizlet user:");
        JLabel label5 = new JLabel("quizlet pass:");
        JTextField textField1 = new JTextField(15);
        JTextField textField2 = new JTextField(15);
        JPasswordField textField3 = new JPasswordField(15);
        JTextField textField4 = new JTextField(15);
        JPasswordField textField5 = new JPasswordField(15);
        JButton button = new JButton("Sumbit");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { allocatevar(textField1.getText(), textField2.getText(), textField3.getPassword(), textField4.getText(), textField5.getPassword(), frame);}
        });
        layout.add(label1);
        layout.add(label2);
        layout.add(label3);
        layout.add(label4);
        layout.add(label5);
        layout.add(textField1);
        layout.add(textField2);
        layout.add(textField3);
        layout.add(textField4);
        layout.add(textField5);
        layout.add(button);
        
        spring.putConstraint(SpringLayout.WEST, label1, 5, SpringLayout.WEST, layout);
        spring.putConstraint(SpringLayout.NORTH, label1, 5, SpringLayout.NORTH, layout);
        spring.putConstraint(SpringLayout.NORTH, textField1, 1, SpringLayout.NORTH, layout);
        spring.putConstraint(SpringLayout.WEST, textField1, 90, SpringLayout.WEST, label1);
        
        spring.putConstraint(SpringLayout.WEST, label2, 5, SpringLayout.WEST, layout);
        spring.putConstraint(SpringLayout.NORTH, label2, 10, SpringLayout.SOUTH, label1);
        spring.putConstraint(SpringLayout.NORTH, textField2, 1, SpringLayout.SOUTH, textField1);
        spring.putConstraint(SpringLayout.WEST, textField2, 90, SpringLayout.WEST, label2);
        
        spring.putConstraint(SpringLayout.WEST, label3, 5, SpringLayout.WEST, layout);
        spring.putConstraint(SpringLayout.NORTH, label3, 10, SpringLayout.SOUTH, label2);
        spring.putConstraint(SpringLayout.NORTH, textField3, 1, SpringLayout.SOUTH, textField2);
        spring.putConstraint(SpringLayout.WEST, textField3, 90, SpringLayout.WEST, label3);
        
        spring.putConstraint(SpringLayout.WEST, label4, 5, SpringLayout.WEST, layout);
        spring.putConstraint(SpringLayout.NORTH, label4, 10, SpringLayout.SOUTH, label3);
        spring.putConstraint(SpringLayout.NORTH, textField4, 1, SpringLayout.SOUTH, textField3);
        spring.putConstraint(SpringLayout.WEST, textField4, 90, SpringLayout.WEST, label4);
        
         spring.putConstraint(SpringLayout.WEST, label5, 5, SpringLayout.WEST, layout);
        spring.putConstraint(SpringLayout.NORTH, label5, 10, SpringLayout.SOUTH, label4);
        spring.putConstraint(SpringLayout.NORTH, textField5, 1, SpringLayout.SOUTH, textField4);
        spring.putConstraint(SpringLayout.WEST, textField5, 90, SpringLayout.WEST, label5);
        
        spring.putConstraint(SpringLayout.NORTH, button, 20, SpringLayout.SOUTH, textField5);
        spring.putConstraint(SpringLayout.WEST, button, 100, SpringLayout.WEST, layout);
        
        spring.putConstraint(SpringLayout.EAST, layout, 5, SpringLayout.EAST, textField5);
        spring.putConstraint(SpringLayout.SOUTH, layout, 5, SpringLayout.SOUTH, button);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        contentPane.add(layout);
        frame.pack();
        frame.setVisible(true);
        
        
 
        
        
        
        }
    }

