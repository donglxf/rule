package com.ht.rule.config.util.aop;

import com.ht.rule.common.api.mapper.DelFindMapper;
import com.ht.rule.config.util.anno.OperationDelete;
import com.ht.rule.common.result.Result;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切点类。查询删除的id是否被其他项引用，而确定是否成功删除
 * @author zhangpeng
 * @since 2015-05-05 Pm 20:35
 * @version 1.0
 */
@Aspect
@Component
@Log4j2
public  class CheckDelAop {
    @Pointcut("@annotation(com.ht.rule.config.util.anno.OperationDelete)")
    public void delFormAnno(){
    }
    @Autowired
    private DelFindMapper delFindMapper;
    /**           
     * 定义查询结果
     */  
     @Around("delFormAnno()")
     public Result<Integer> cache(ProceedingJoinPoint pjp ) {
		 Result<Integer>  result = null;
		try {
			Method method = getMethod(pjp);
			//获取注解中数据
			OperationDelete delFormAnno = method.getAnnotation(OperationDelete.class);
			//删除的id值的 SPEL表达式
			String idSpel = delFormAnno.idVal();
			//需要查询的表
			String[] tables = delFormAnno.tableColumn();
			//获取id的值
			Object idVal = getDelIdVal(idSpel,method,pjp.getArgs());
			for (int i = 0; i < tables.length; i++) {
				String[] table_cum = tables[i].split("&");
				String table = table_cum[0];
				String cum = table_cum[1];
				String where = "";
				if(table_cum.length > 2){
					where = table_cum[2];
				}
				//执行数据库操作
				Integer count = delFindMapper.findCount(table,cum,idVal,where);
				if(count > 0){
					return Result.error(-1,"删除失败，改数据正被其他数据引用");
				}
			}
			//查询结果
			result = (Result<Integer> )pjp.proceed();
			return result;
		} catch (Throwable e) {
			log.error("查询搜索id是否被引用出差");
			e.printStackTrace();
			return Result.error(-500,"系统异常");
		}  
     }
	/**
	 *  获取被拦截方法对象
	 *
	 *  MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
	 *    而缓存的注解在实现类的方法上
	 *  所以应该使用反射获取当前对象的方法对象
	 */
	public Method getMethod(ProceedingJoinPoint pjp) {
		// 获取参数的类型
		Object[] args = pjp.getArgs();
		Class[] argTypes = new Class[pjp.getArgs().length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		Method method = null;
		try {
			method = pjp.getTarget().getClass()
					.getMethod(pjp.getSignature().getName(), argTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return method;
	}

	/**
	 * 获取id的值
	 * @param idSpel
	 * @param method
	 * @param args
	 * @return
	 */
	private Object getDelIdVal(String idSpel,Method method,Object[] args) {
		// 获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = u.getParameterNames(method);
		// SPEL上下文
		StandardEvaluationContext context = new StandardEvaluationContext();
		StandardEvaluationContext spacecontext = new StandardEvaluationContext();
		// 把方法参数放入SPEL上下文中
		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
			spacecontext.setVariable(paraNameArr[i], args[i]);
		}
		if(StringUtils.isBlank(idSpel)){
			return "-1";
		}else{
			// 使用SPEL进行key的解析
			ExpressionParser parser = new SpelExpressionParser();
			/**
			 * sqel表达式获取的参数值
			 * */
			Object delIdVal = parser.parseExpression(idSpel).getValue(spacecontext, String.class);
			return delIdVal;
		}


	}

}
