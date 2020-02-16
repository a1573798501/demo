package open.zzj.demo.demo.advice;


import com.alibaba.fastjson.JSON;
import com.mysql.cj.xdevapi.JsonString;
import open.zzj.demo.demo.dto.ResultDto;
import open.zzj.demo.demo.exception.CustomizeErrorCode;
import open.zzj.demo.demo.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                  Throwable ex,
                  Model model,
                  HttpServletResponse response) {
//        HttpStatus status = getStatus(request);

        String contentType = request.getContentType();


        if (contentType != null && contentType.equals("application/json")) {
            ResultDto resultDto = null;
            //返回json
            if (ex instanceof CustomizeException) {
                resultDto = ResultDto.errorOf((CustomizeException)ex);
            } else {
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JSON.toJSONString(resultDto));
                printWriter.close();
            }catch (IOException ioe){

            }
            return null;

        } else {
            //错误页面跳转
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }

}
