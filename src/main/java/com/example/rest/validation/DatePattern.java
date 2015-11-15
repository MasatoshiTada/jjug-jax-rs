package com.example.rest.validation;

import com.example.rest.util.DateUtil;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * 日付の形式を指定できる自作検証アノテーションです。
 * @author tada
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DatePattern.DatePatternValidator.class})
public @interface DatePattern {
    
    /**
     * 日付の形式を「yyyy-MM-dd」のように指定します。
     * @return 
     */
    String pattern();
    
    // 以下は検証アノテーションを自作する際のお決まりコード
    Class<?>[] groups() default {};
    String message() default "Date is not valid.";
    Class<? extends Payload>[] payload() default {};
    
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        DatePattern[] value();
    }
    
    /**
     * 実際にバリデーションを行うクラス。
     */
    class DatePatternValidator implements ConstraintValidator<DatePattern, String> {
        
        private String pattern;
        
        @Override
        public void initialize(DatePattern constraintAnnotation) {
            this.pattern = constraintAnnotation.pattern();
        }

        /**
         * 
         * @param value 実際に入力された値
         * @param context
         * @return バリデーションを行い、適切な値であればtrue、そうでなければfalse
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return DateUtil.isValidString(value, pattern);
        }
        
    }
}
