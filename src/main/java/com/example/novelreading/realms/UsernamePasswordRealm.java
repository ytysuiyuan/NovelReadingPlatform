//package com.example.novelreading.realms;
//
//import com.example.novelreading.entity.User;
//import com.example.novelreading.service.IUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.realm.AuthenticatingRealm;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Slf4j
//@Component
//public class UsernamePasswordRealm extends AuthenticatingRealm {//只管验证不管鉴权
//
//    @Resource
//    private IUserService userService;
//
//    /*构造器里配置Matcher*/
//    public UsernamePasswordRealm() {
//        super();
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        hashedCredentialsMatcher.setHashIterations(1024);//密码保存策略一致，1024次md5加密
//        this.setCredentialsMatcher(hashedCredentialsMatcher);
//    }
//
//    /**
//     * 通过该方法来判断是否由本realm来处理login请求
//     *
//     * 调用{@code doGetAuthenticationInfo(AuthenticationToken)}之前会shiro会调用{@code supper.supports(AuthenticationToken)}
//     * 来判断该realm是否支持对应类型的AuthenticationToken,如果相匹配则会走此realm
//     *
//     * @return
//     */
//    @Override
//    public Class getAuthenticationTokenClass() {
//        log.info("UsernamePasswordRealm getAuthenticationTokenClass");
//        return UsernamePasswordToken.class;
//    }
//
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        //继承但啥都不做就为了打印一下info
//        boolean res = super.supports(token);//会调用↑getAuthenticationTokenClass来判断
//        log.info("[UsernamePasswordRealm is supports]" + res);
//        return res;
//    }
//
//    /**
//     * 用户名和密码验证，login接口专用。
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
//        // 将 token 转换为 UsernamePasswordToken
//        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
//
//        // 从数据库中根据用户名获取用户
//        User userFromDB = userService.getUserByUsername(usernamePasswordToken.getUsername());
//
//        // 检查用户是否存在
//        if (userFromDB == null) {
//            // 用户未找到，返回 null
//            return null;
//        }
//
//        // 获取数据库中的密码和盐值
//        String passwordFromDB = userFromDB.getPassword();
//        String salt = userFromDB.getSalt();
//
//        // 创建并返回 SimpleAuthenticationInfo 对象
//        // 这里只进行基本的密码验证，不处理角色和权限
//        SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(
//                userFromDB, // 用户对象
//                passwordFromDB, // 数据库中的密码
//                ByteSource.Util.bytes(salt), // 盐值
//                getName() // Realm 名称
//        );
//
//        return authInfo;
//    }
//
//}
