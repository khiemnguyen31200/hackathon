package vn.techmaster.job_hunt.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
	
	public void sendEmail(String empEmail, String jobTitle) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(empEmail);

        msg.setSubject(jobTitle + " Apply");
        msg.setText("Dear Mr/Ms.HR \n I was keenly interested in reading the job posting for the position of Anatomy and Physiology Professor at Middleburg University. I believe my experience is a strong match for the responsibilities pertaining to this role, and I’m pleased to submit my application for the position. "
        
       + "\n My most recent teaching position was at Amery University, where I taught both anatomy and physiology as an adjunct professor. In addition, I served on two faculty committees and participated in a research project."
        
       + "\n I have attached my resume to this letter. Through it, I hope you will learn more about my background, education, achievements, and awards."
        
       + "\n If I can provide you with any further information, please let me know. I look forward to hearing from you about this opportunity."
        
       + "\n Thank you for your consideration."
        
        +"\n \n Jane Lee"
        +"\n Jane.Lee@email.com"
       + "\n 454-555-4653");

        javaMailSender.send(msg);

    }

    public void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		
        helper.setTo("to_@email");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

		// hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }
 
}
