###   1.本地用mysql创建数据库bootcamp_test
> 项目用idea基于maven构建,框架为springboot
- 用script.sql创建表user和call_record
- 修改src/main/java/resources/application.properties中username和password为你本地mysql中的
- 启动mysql服务
  
- 对着src/main/java/cn.creditease下的BootCampApplication右键,run起来
- 修改RestClientUtil文件main函数中调用函数loadUser2DB和loadCallLog2DB时的参数为对应文件的路径
- 对着src/test/java/cn.creditease.client下的RestClientUtil右键,run