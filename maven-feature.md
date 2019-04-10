
# Maven特性

## version分为开发版本（Snapshot）和发布版本（Release），为什么要分呢？
比如A服务依赖于B服务，A和B同时开发，在开发过程中B发布的版本标志为Snapshot版本，
A进行依赖的时候选择Snapshot版本，那么每次B发布的话，会在私服仓库中，
形成带有时间戳的Snapshot版本，而A构建的时候会自动下载B最新时间戳的Snapshot版本！

## 依赖传递性：
如果A依赖B，B依赖C，那么引入A则意味着B和C都会被引入

## 最近依赖策略：
如果一个项目依赖相同的groupId、artifactId的多个版本，那么在依赖树（mvn dependency:tree）中离项目最近的那个版本将会被使用。
（能不能选择高版本的进行依赖？据了解，Gradle就是version+策略）

## 如何解决依赖冲突？
1、使用<dependencyManagement> （这种主要用于子模块的版本一致性中）
2、使用<exclusions> 
3、使用<dependency> （既然是最近依赖策略，直接使用显式依赖指定版本，不就是最靠近项目的么）

## 常用Maven生命周期：（执行后面的命令时，前面的命令自动得到执行）
clean：有问题，多清理！
package：打成Jar or War包，会自动进行clean+compile
install：将本地工程Jar上传到本地仓库
deploy：上传到私服

## scope依赖范围
compile：默认的scope，运行期有效，需要打入包中
provided：编译期有效，运行期不需要提供，不会打入包中 （如：servlet-api）
runtime：编译不需要，在运行期有效，需要导入包中 （接口与实现分离，如：mysql的驱动包）
test：测试需要，不会打入包中 （如：Junit）
system：非本地仓库引入、存在系统的某个路径下的jar （一般不使用）

