
-- 若数据不足两行，不会返回 null，而是什么都不返回
select distinct Salary as SecondHighestSalary from Employee order by Salary desc limit 1, 1;

-- 嵌套 select 是为了返回 null。内层 select 作为表达式，必须产生一个计算结果
select (
select distinct Salary from Employee order by Salary desc limit 1, 1
) as SecondHighestSalary;

-- 使用 IFNULL 函数
select IFNULL(
(select distinct Salary from Employee order by Salary desc limit 1 offset 1),
null
) as SecondHighestSalary
