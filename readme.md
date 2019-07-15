#registryCenter

registryCenter是基于etcd和java语言开发的服务注册中心,为公司的游戏分布式架构开发,但是没有和具体的业务逻辑关联,可以单独使用.开发过程中参考了dubbo的注册中心

1. center 负责暴露业务接口,本机服务的注册和监听其他服务都调用这个接口的方法
2. registry 负责和第三方的etcd数据处理,可以继承registry实现zookeeper版本,redis版本(没有开发计划)
3. service 服务抽象类,包含一个服务的基本数据和状态改变的接口.自己使用的时候还需要实现网络连接才能完全调用,公司的项目采用长连接,所以在我的实际使用中继承实现了一个tcpService,包含了一个connect对象,包含了自动重连方法等等
4. factory 服务的工厂,实现服务类的factory注册到center中去,就可以使用这个服务实现类了
5. updateEvent 服务状态变更事件