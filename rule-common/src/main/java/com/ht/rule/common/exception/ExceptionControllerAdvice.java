package com.ht.rule.common.exception;

import com.ht.rule.common.exception.MyException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * controller 增强器
 *
 * @author sam
 * @since 2017/7/17
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
	private Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String,Object > errorHandler(Exception ex) {
        //参数验证异常 MethodArgumentNotValidException
        if(ex instanceof ConstraintViolationException){
            return  validationExceptionHandle(ex);
        }
    	Map<String,Object >  map = new HashMap<String,Object >();
        map.put("code", -500);
        map.put("msg", "系统异常:"+ex.getMessage());
        map.put("data", null);
        logger.error("=========打印日志开始============");
        logger.error(ex.getMessage());
        ex.printStackTrace();
        logger.error("=========打印日志结束============");
        return map;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String,Object > handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMesssage = "Invalid Request:\n";
        Map<String,Object >  map = new HashMap<String,Object >();
        map.put("code", -400);
        map.put("msg", "请求参数错误:\n");
        map.put("data", errorMesssage);
        logger.error("=========验证不通过哦~============");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "\n";
            logger.error(fieldError.getDefaultMessage());
        }
        map.put("msg", "请求参数错误:\n"+errorMesssage);
        map.put("data",errorMesssage);
        return map;
    }
    private Map<String,Object > validationExceptionHandle(Exception exception) {
        Map<String,Object >  map = new HashMap<String,Object >();
        String mess = "";
        map.put("code", -501);
        map.put("msg", "请求参数错误");
        map.put("data", mess);
        if(exception instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            logger.error("=========验证不通过哦~============");
            for (ConstraintViolation<?> item : violations) {
                //错误信息打印
                logger.error(item.getMessage());
                if(StringUtils.isBlank(mess)){
                    mess = item.getMessage();
                }else{
                    mess += ","+item.getMessage();
                }
            }
            map.put("data", mess);
            return map;
        }
        return map ;
    }
    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object > handle(ValidationException exception) {
      return  validationExceptionHandle(exception);
    }
    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = com.ht.rule.common.exception.MyException.class)
    public Map<String,Object > myErrorHandler(MyException ex) {
        Map<String,Object > map = new HashMap<String,Object >();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        map.put("data", null);
        return map;
    }

}