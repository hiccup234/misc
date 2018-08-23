package com.hiccup.misc.regex;

/**
 * 正则表达式不当使用导致CPU利用率将近100%
 *  --回溯陷阱（Catastrophic Backtracking）
 *
 * 【正则表达式语法】
 * \	将下一个字符标记为或特殊字符、或原义字符、或向后引用、或八进制转义符。例如， 'n' 匹配字符 'n'。'\n' 匹配换行符。序列 '\\' 匹配 "\"，而 '\(' 则匹配 "("
 * ^	匹配输入字符串的开始位置，除非在方括号表达式中使用，此时它表示不接受该字符集合。要匹配 ^ 字符本身，请使用 \^
 * $	匹配输入字符串的结尾位置。如果设置了 RegExp 对象的 Multiline 属性，则 $ 也匹配 '\n' 或 '\r'。要匹配 $ 字符本身，请使用 \$
 * ( )	标记一个子表达式的开始和结束位置。子表达式可以获取供以后使用。要匹配这些字符，请使用 \( 和 \)
 * ?	匹配前面的子表达式零次或一次，或指明一个非贪婪限定符。要匹配 ? 字符，请使用 \?
 * *	匹配前面的子表达式零次或多次。要匹配 * 字符，请使用 \*
 * +	匹配前面的子表达式一次或多次。要匹配 + 字符，请使用 \+
 * .	匹配除换行符 \n 之外的任何单字符。要匹配 . ，请使用 \.
 * [	标记一个中括号表达式的开始。要匹配 [，请使用 \[
 * {	标记限定符表达式的开始。要匹配 {，请使用 \{
 * |	指明两项之间的一个选择。要匹配 |，请使用 \|

 *
 * 【正则表达式引擎】
 * DFA 自动机（Deterministic Final Automata 确定型有穷自动机）：
 * 复杂度是线性的，更加稳定，但支持的特性很少，不支持捕获组、各种引用等等
 *
 * NFA 自动机（Non deterministic Finite Automaton 不确定型有穷自动机）：
 * 时间复杂度比较不稳定，有时候很好，有时候不怎么好，好不好取决于你写的正则表达式
 * 但是胜在 NFA 的功能更加强大，所以包括 Java 、.NET、Perl、Python、Ruby、PHP 等语言都使用了 NFA 去实现其正则表达式
 *
 *
 *
 * 贪婪     懒惰     独占
 * X?      X??      X?+
 * X*      X*?      X*+
 * X+      X+?      X++
 * X{n}    X{n}?    X{n}+
 * X{n,}   X{n,}?   X{n,}+
 * X{n,m}  X{n,m}?  X{n,m}+
 *
 * https://regex101.com
 *
 * @author wenhy
 * @date 2018/8/22
 */
public class RegexTest {

    public static void main(String[] args) {





        // 第一部分匹配 http 和 https 协议
        // 第二部分匹配 www. 字符
        // 第三部分匹配许多字符
        String badRegex = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)++([A-Za-z0-9-~\\\\/])+$";
        String bugUrl = "http://www.javastack.cn/dzfp-web/pdf/download?request=6e7JGm38jfjghVrv4ILd-kEn64HcUX4qL4a4qJ4-CHLmqVnenXC692m74H5oxkjgdsYazxcUmfcOH2fAfY1Vw__%5EDadIfJgiEf";
        if(bugUrl.matches(badRegex)) {
            System.out.println("bingo");
        } else {
            System.out.println("not match");
        }
        // 其实这里导致 CPU 使用率高的关键原因就是：Java 正则表达式使用的引擎实现是 NFA 自动机，
        // 这种正则表达式引擎在进行字符匹配时会发生回溯（backtracking）。
        // 而一旦发生回溯，那其消耗的时间就会变得很长，有可能是几分钟，也有可能是几个小时，时间长短取决于回溯的次数和复杂度。

    }
}
