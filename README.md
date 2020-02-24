##  我的新的Spring demo


hello

## 命令

#### *git*命令
```$xslt
git init
git status
git add .
git commit -m "init repo"
git remote add origin https://github.com/a1573798501/demo.git
git push -u origin master

```


#### *flyway* 命令
```
mvn flyway:migrate

```

#### *maven*编译springboot项目
mvn install

mvn compiler:compile

mvn org.apache.maven.plugins:maven-compiler-plugin:compile

mvn org.apache.maven.plugins:maven-compiler-plugin:2.0.2:compile

#### *linux* screen的使用

为了服务端的某些进程挂起，可以使用screen创建session并后台挂起，使用命令如下：

screen -S yourname -> 新建一个叫yourname的session

screen -ls         -> 列出当前所有的session

screen -r yourname -> 回到yourname这个session

screen -d yourname -> 远程detach某个session

screen -d -r yourname -> 结束当前session并回到yourname这个session
