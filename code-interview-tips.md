# Content

* [Content](#content)
* [duration](#duration)
* [intro & outro](#intro--outro)
* [basic steps](#basic-steps)
* [加test](#加test)
* [有必要问的边界](#有必要问的边界)

# duration

30-45min

# intro & outro

intro - 1~2min

Q&A - 3~5min

![](/static/2022-06-04-16-11-17.png)

# basic steps

ask for the webcam to be turned off -> focus when coding (vi)

电面

1. ask for clarification
   1. input, type
   2. input range
   3. repeat
   4. **assumptions (raise some examples, ask whether output example is expected**)
      1. 多写一些test cases 这样在写每一个的时候 你的input and expected output可以跟面试官验证
   5. border cases
   6. 输入是否可以变更，覆盖
2. clarify all possible solutions & complexity (5~10min)
   1. **解释能想到的【所有solutions】, 讨论每种权衡【基本基于复杂度**，提出折衷方案，解释复杂度
      1. 清楚和正确地解释了每个解决方案的权衡，并得出结论认为哪个解决方案最适合当前的情况
      2. 有时间讨论后续问题/扩展
      3. <font color="deeppink">注意1先问复杂度有没有限制，要求</font>
      4. <font color="red">优先考虑不会有global var mutation的解,,</font>
   2. **解释折衷最优方案如何优化（重复计算，cache多次使用的属性，剪枝**
3. writing
   1. **注意变量名规范，尽可能详细**
   2. **分模块编写，比如swap, isValid，或者ask是否能直接不分开编写辅助函数**
   3. <font color="deeppink">熟悉API直接用，但要解释</font>
4. write(extra, border) & run tests
   1. 脑子跑tests, 解释复杂性等，找可重构地方
   2. 提出更多测试用例，&跑tests。识别&自我纠正bug, 系统方式验证代码正确性，**逐步更新state**
   3. <font color="deeppink">解释，如果时间充足，如何改进</font>
      1. 针对前面提出的所有可能solutions的follow-up，或者针对一些constraints去掉后如何解决

# 加test

正式开始之前 先在面试官给你的example的基础上 想一个新的example

这样你可以跟面试官确认 按照你的理解的output是不是符合题意的

然后可以多写一些test cases 这样在写每一个的时候 你的input and expected output可以跟面试官验证

至于用不用比如一些unit test的library 这个你自己决定 python之类比较容易写unit test的 时间允许的话 我觉得那肯定是更好了 不过一般hardcode一下也都可以了 还有一个交流小技巧就是 你可以一直跟面试官说 “我现在hardcode 一会儿时间够的话 我再写unit test”

# 有必要问的边界

先重复复述题目，能方便举自己例子input, output的一定先根据这个来复述.. 然后一般一开始抽象做法讲一下怎么解的

<font color="red">暂时想不到edge，强调先说思路，但是后面写代码的时候发现任何问题再clarify一下</font>

1. 输入是否允许为空
   * 为空怎么处理，怎么返回
2. 输入类型
   1. char, int, double之类的
3. 输入范围有限制吗
   1. 有限制的注意数值位数限制，，Double, Integer
4. 复杂度有要求吗？
5. 输入如果是数字数组，
   1. 递增？递减？有序？无序？