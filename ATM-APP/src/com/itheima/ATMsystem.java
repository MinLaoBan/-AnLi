package com.itheima;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ATM的入口系统
 */
public class ATMsystem {
    public static void main(String[] args) {
        //1.定义账户类型
        //2.定义一个集合容器，负责以后存储的账户对象，进行相关的业务操作
        ArrayList<Account> accounts = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        //3. 展示系统的首页
        while (true) {
            System.out.println("================黑马ATM系统=================");
            System.out.println("1、账户登录");
            System.out.println("2、账户注册");
            System.out.println("请您进行操作：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //用户登录操作
                    login(accounts,sc);
                    break;
                case 2:
                    //用户注册操作
                    register(accounts,sc);
                    break;
                default:
                    System.out.println("您输入的操作命令不存在，请重新选择");
            }
        }
    }


    /**
     * 登录功能
     * @param accounts 全部对象的集合
     * @param sc  扫描器
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("==============登陆系统==================");
        //1.判断账户集合中是否存在账户，如果不存在，登录功能不能进行
        if (accounts.size() ==0) {
            System.out.println("对不起，当前系统无任何账户，请先开户，再来登录~~~");
            return;//直接退出
        }
        //2.正式进入登录操作
        while (true) {
            System.out.println("请先输入卡号");
            String cardId = sc.next();
            //3. 判断卡号是否存在，根据卡号去账户集合中查询账户对象
            Account acc = getAccountByCardId(cardId,accounts);
            if (acc != null) {
                //卡号存在的
                while (true) {
                    //4.让用户输入密码，认证密码
                    System.out.println("请您输入密码");
                    String passworld = sc.next();
                    if (acc.getPassWorld().equals(passworld)) {
                        //登录成功了
                        System.out.println("恭喜您！" + acc.getUserName() + "先生/女士，恭喜您进去系统，您的卡号是" + acc.getCarId());
                        //...查询 转帐  取款...
                        //展示登陆后的操作页
                        showUsercommand(sc, acc, accounts);

                        return;//干掉登录方法
                    }else {
                        System.out.println("您输入的密码错误请重新输入");
                    }
                }
            }else {
                System.out.println("对不起，系统中不存在该账户的卡号");
            }
        }
    }

    /**
     * 展示登录后的操作页
     */
    private static void showUsercommand(Scanner sc, Account acc, ArrayList<Account> accounts) {
        while (true) {
            System.out.println("==============用户操作页================");
            System.out.println("1、查询账户");
            System.out.println("2、存款");
            System.out.println("3、取款");
            System.out.println("4、转账");
            System.out.println("5、修改密码");
            System.out.println("6、退出");
            System.out.println("7、注销账户");
            System.out.println("请选择：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //查询账户
                    showAccount(acc);
                    break;
                case 2:
                    //存款
                    depositMoney(acc, sc);
                    break;
                case 3:
                    //取款
                    drawMoney(acc,sc);
                    break;
                case 4:
                    //转账
                    transferMoney(sc, acc, accounts);
                    break;
                case 5:
                    //修改密码
                    updatePassWorld(sc, acc);
                    return;//让当前方法停止执行，跳出去
                case 6:
                    //退出
                    System.out.println("退出成功，欢迎下次光临");
                    return;//让当前方法停止执行，跳出去
                case 7:
                    //注销账户
                    //从当前账户集合中，删除当前账户对象，销毁就完成了
                    if (deleteAccount(acc, sc, accounts)){
                        //销户成功回到首页
                        return;//让当前方法停止执行，跳出去
                    }else {
                        //没有销户成功，还在操作页玩
                        break;
                    }
                default:
                    System.out.println("没有此选择，请重新选择");
            }
        }
    }


    /**
     * 销户功能
     * @param acc
     * @param sc
     * @param accounts
     */
    private static boolean deleteAccount(Account acc, Scanner sc, ArrayList<Account> accounts) {
        System.out.println("=================用户销户===================");
        System.out.println("您真的要销户吗？确定请输入y");
        String rs = sc.next();
        switch (rs){
            case "y":
                //真正的销户
                //从当前账户集合中，删除当前账户对象，销毁就完成了
                if (acc.getMoney() > 0) {
                    System.out.println("您账户中还有钱没有取完，不允许销户");
                }else {
                    accounts.remove(acc);
                    System.out.println("您的账户销毁完成~~");
                    return true;
                }
                break;
            default:
                System.out.println("当前账户继续保留");
        }
        return false;//销户失败
    }

    /**
     * 修改密码
     * @param sc 扫描器
     * @param acc 当前登录成功的账户对象
     */
    private static void updatePassWorld(Scanner sc, Account acc) {
        System.out.println("=================用户密码修改=================");
        while (true) {
            System.out.println("请您输入当前密码");
            String passworld = sc.next();
            //判断密码是否正确
            if (passworld.equals(acc.getPassWorld())) {
                while (true) {
                    //密码正确
                    //2.输入新密码
                    System.out.println("请您输入新密码");
                    String newPassWorld = sc.next();

                    System.out.println("请您确认新密码");
                    String oknewPassWorld = sc.next();
                    if (newPassWorld.equals(oknewPassWorld)) {
                        //2次密码一致，可以修改密码
                        acc.setPassWorld(newPassWorld);
                        System.out.println("恭喜你，密码修改成功");
                        return;
                    }else {
                        System.out.println("您输入的2次密码不一样");
                    }
                }
            }else {
                System.out.println("您输入的密码不正确");
            }
        }
    }

    /**
     * 转账功能
     * @param sc 扫描器
     * @param acc 自己的账户对象
     * @param accounts 全部账户的集合
     */
    private static void transferMoney(Scanner sc, Account acc, ArrayList<Account> accounts) {
        System.out.println("=================用户转账操作=================");
        //1.判断是否足够两个账户
        if (accounts.size() < 2 ) {
            System.out.println("当前系统中，不足2个账户，不能转账，请去开户~~");
            return;//结束此方法
        }
        //2.判断自己的账户是否有钱
        if (acc.getMoney() == 0) {
            System.out.println("对不起，您自己都没钱，就别转账了~");
            return;
        }
        while (true) {
            //3.真正开始转账
            System.out.println("请您输入对方账户的卡号");
            String cardId = sc.next();

            //这个卡号不能是自己的卡号
            if (cardId.equals(acc.getCarId())) {
                System.out.println("对不起，您不可以给自己转账");
                continue;//结束当此执行，进入下一次
            }

            //判断卡号是否存在：根据这个卡号去查询账户对象
            Account account = getAccountByCardId(cardId,accounts);
            if (account == null) {
                System.out.println("对不起，您输入的对方账户不存在");
            }else {
                //这个对方账户对象存在：继续认证他的姓氏
                String userName = account.getUserName();
                String tip = "*" + userName.substring(1);
                System.out.println("请您输入[" + tip +"]的姓氏");
                String preName = sc.next();

                //认证姓氏是否输入正确
                if (userName.startsWith(preName)) {
                    while (true) {
                        //认证通过，开始正式转账
                        System.out.println("请您输入转账金额");
                        double money = sc.nextDouble();
                        if (money > acc.getMoney()){
                            System.out.println("对不起您余额不足，您最多可以转" + acc.getMoney());
                        }else {
                            //余额足够
                            acc.setMoney(acc.getMoney() - money);
                            account.setMoney(account.getMoney() + money);
                            System.out.println("转账成功，您的账户还剩" + acc.getMoney());
                            return;
                        }
                    }
                }else {
                    System.out.println("对不起，您输入的信息有错");
                }
            }
        }
    }


    /**
     * 取钱功能
     * @param acc 当前账户的对象
     * @param sc  扫描器
     */
    private static void drawMoney(Account acc, Scanner sc) {
        System.out.println("=================用户取钱操作=================");
        //1.判断是否足够100元
        if (acc.getMoney() < 100) {
            System.out.println("对不起，当前账户中不足100元，不能取钱");
            return;
        }
        while (true) {
            //2.提示用户取钱金额
            System.out.println("请您输入取款金额");
            double money = sc.nextDouble();

            //3.判断金额是否满足要求
            if (money > acc.getQuotaMoney()) {
                System.out.println("对不起，您当前取款金额超过每次限额，每次最多可取" + acc.getQuotaMoney());
            }else {
                //没有超过限额
                //4.是否超过了账户的总余额
                if (money > acc.getMoney()) {
                    System.out.println("余额不足，你当前余额为" + acc.getMoney());
                }else {
                    //可以取钱
                    System.out.println("恭喜您取钱" + money + "元，成功");
                    //更新余额
                    acc.setMoney(acc.getMoney() - money);
                    //取钱结束
                    showAccount(acc);
                    return;//干掉取钱方法
                }
            }
        }

    }

    /**
     * 存钱
     * @param acc  当前账户对象
     * @param sc 扫描器
     */
    private static void depositMoney(Account acc, Scanner sc) {
        System.out.println("=================用户存钱操作=================");
        System.out.println("请您输入要存的金额");
        double money = sc.nextDouble();
        //更新账户余额 = 原来的钱 + 存入的钱
        acc.setMoney(acc.getMoney() + money);
        System.out.println("恭喜你，存钱成功，当前信息如下：");
        showAccount(acc);
    }

    /**
     * 展示账户信息
     * @param acc
     */
    private static void showAccount(Account acc) {
        System.out.println("==================当前账户如下==============");
        System.out.println("卡号：" + acc.getPassWorld());
        System.out.println("户主：" + acc.getUserName());
        System.out.println("余额：" + acc.getMoney());
        System.out.println("限额：" + acc.getQuotaMoney());
    }

    /**
     * 用户开户的功能实现
     * @param accounts 接收的账户集合
     */
    private static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("==============系统开户操作==============");
        //1.创建一个开户对象，用于后期封装账户信息
        Account account = new Account();


        //2.录入当前这个账户的信息，注入到账户对象中去
        System.out.println("请您输入账户名");
        String userName = sc.next();
        account.setUserName(userName);

        while (true) {
            System.out.println("请您输入账户密码");
            String passworld = sc.next();
            System.out.println("请您输入确认密码");
            String okpassworld = sc.next();
            if (okpassworld.equals(passworld)) {
                //密码认证通过，可以注入给账户对象
                account.setPassWorld(okpassworld);
                break;//密码录入成功
            }else {
                System.out.println("对不起，您输入的2次密码不一致，请重新确认");
            }
        }

        System.out.println("请您输入账户当此限额");
        double quotaMoney = sc.nextDouble();
        account.setQuotaMoney(quotaMoney);


        //为账户随机一个8位且与其他账户不重复的卡号(独立功能，独立成方法)
        String cardId = getRandomCardId(accounts);
        account.setCarId(cardId);
        //3.把账户对象添加到账户集合中去
        accounts.add(account);
        System.out.println("恭喜您" + userName + "先生/女士，您开户成功，您的卡号是" + cardId + ",请您妥善保管卡号");
    }

    /**
     * 为为账户随机一个8位且与其他账户不重复的号码
     * @return
     */
    private static String getRandomCardId(ArrayList<Account> accounts) {
        Random r = new Random();
        while (true) {
            //1.先生成id
            String cardId = "";
            for (int i = 0; i < 8; i++) {
                cardId += r.nextInt(10);
            }

            //2.判断这八位卡号是否与其他账户重复
            //根据这个卡号去查询账户对象
            Account acc = getAccountByCardId(cardId,accounts);
            if (acc == null) {
                //说明cardid没有重复，可以注册
                return cardId;
            }
        }

    }


    /**
     *根据卡号查询出一个账户对象出来
     * @param cardId 卡号
     * @param accounts  全部账户的集合
     * @return 账户对象|null
     */
    public static Account getAccountByCardId(String cardId, ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getCarId().equals(cardId)) {
                return acc;
            }
        }
        return null;//查无此账户
    }


}
