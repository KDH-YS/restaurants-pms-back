package com.mysite.restaurant.hj.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;
	private int authNumber;
	
	@Autowired
    private  RedisUtil redisUtil;
	
	public boolean CheckAuthNum(String email, String authNum) {
        if (redisUtil.getData(authNum) == null) {
            return false;
        }
        else if (redisUtil.getData(authNum).equals(email)) {
//        	인증이 완료되면 Redis에서 인증 번호 삭제
        	redisUtil.deleteData(authNum);
            return true;
		}
		else {
		    return false;
		}
    }
	
//	임의의 6자리 양수를 반환
	public void makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber);
    }
	
//	mail을 어디서 보내는지, 어디로 보내는지 , 인증 번호를 html 형식으로 어떻게 보내는지 작성
	public String joinEmail(String email) {
		makeRandomNumber();
		String setFrom = "dionisos198@naver.com"; // email-config에 설정한 자신의 이메일 주소를 입력
		String toMail = email;
		String title = "레슐랭코리아 회원가입 인증 이메일 입니다."; // 이메일 제목
		String content =
		        "레슐랭코리아를 방문해주셔서 감사합니다." + // html 형식으로 작성!
			    "<br><br>" +
			    "인증 번호는 " + authNumber + "입니다." +
			    "<br>" +
			    "인증번호를 입력해주세요"; // 이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		
//		인증 번호와 이메일을 Redis에 저장하고, 5분 후 만료되도록 설정
		redisUtil.setDataExpire(Integer.toString(authNumber), email, 300); // 300초(5분)
		
		return Integer.toString(authNumber);
    }
	
//	이메일 전송
	public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage(); // JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // 이메일 메시지와 관련된 설정을 수행
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom); // 이메일의 발신자 주소 설정
            helper.setTo(toMail); // 이메일의 수신자 주소 설정
            helper.setSubject(title); // 이메일의 제목을 설정
            helper.setText(content, true); // 이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);
        } catch (MessagingException e) { // 이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace(); // e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }
        

    }
}
