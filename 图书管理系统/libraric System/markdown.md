#📚 项目简介
一个基于Java的图书管理系统，提供图书信息管理、借阅管理、搜索功能和逾期检测等功能，数据使用文本文件持久化存储。

------------


### 🛠️ 安装要求
1. 系统要求
Java Development Kit (JDK) 17或更高版本

1. 任何支持Java的操作系统（Windows/macOS/Linux）

1. 开发环境（可选）
IntelliJ IDEA/Eclipse等Java IDE

1. Git（用于版本控制）

------------


### 🚀 快速开始
**1. 获取项目代码**
bash
git clone https://github.com/yourusername/LibraryManagementSystem.git
cd LibraryManagementSystem
##### 2. 编译项目
bash
javac -d bin src/*.java
##### 3. 运行程序
bash
java -cp bin Main

------------


### 📂 项目结构
text
LibraryManagementSystem/
├── src/         # Java源代码
│   ├── Book.java
│   ├── BorrowRecord.java
│   ├── FileDataService.java
│   ├── LibraryService.java
│   ├── MenuService.java
│   └── Main.java
├── data/        # 数据存储目录（自动创建）
│   ├── 图书信息.txt
│   └── 借阅记录.txt
└── docs/        # 文档
    ├── 设计文档.md
    └── 测试报告.md
### 🖥️ 使用指南
> #### 主菜单功能
text
==== 图书管理系统 ====
1. 图书管理
2. 借阅管理
3. 查询图书
4. 查看逾期记录
0. 退出
1. 图书管理
添加图书：输入书名、ISBN和作者

查看所有图书：显示当前所有图书信息

**2. 借阅管理**
借书：输入学生卡号和图书ISBN

还书：输入学生卡号和图书ISBN

**3. 查询图书**
支持按书名或作者关键词模糊搜索

**4. 查看逾期记录**
显示所有借阅超过30天未归还的图书

------------


#### ⚠️ 注意事项
首次运行会自动创建data目录

所有数据保存在data/目录下的文本文件中

ISBN是图书的唯一标识，不可重复

------------


#### 📝 示例数据
系统初始化时可添加以下测试数据：

text
书名: Java编程思想
ISBN: 9787111213826
作者: Bruce Eckel

书名: Effective Java  
ISBN: 9787111553078
作者: Joshua Bloch
🐛 常见问题
Q1: 如何重置系统数据？
删除data/目录下的所有文件，重启程序即可

Q2: 数据文件可以手动编辑吗？
可以，但请确保：

保持文本文件格式一致

编辑时程序未运行

修改后检查格式是否正确

------------


#### 📜 许可信息
本项目采用 MIT License

------------


#### 📧 联系方式
如有问题请联系：asdas@example.com