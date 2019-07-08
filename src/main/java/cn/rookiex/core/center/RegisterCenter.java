package cn.rookiex.core.center;

import cn.rookiex.core.service.Service;
import cn.rookiex.core.updateEvent.ServiceUpdateEvent;

import java.util.List;

/**
 * @Author : Rookiex
 * @Date : 2019/07/03
 * @Describe :
 */
public interface RegisterCenter {
    /**
     * 服务注册
     * */
    void register(String serviceName, String ip);

    /**
     * 服务注册取消
     * */
    void unRegister(String serviceName, String ip);

    /**
     * 订阅服务
     * */
    void watch(String serviceName);

    /**
     * 取消订阅服务
     * */
    void unWatch(String serviceName);

    /**
     * 获得随机的service节点
     * */
    Service getRandomService(String serviceName);

    /**
     * 获得所有的service节点
     * */
    List<Service> getServiceList(String serviceName);

    /**
     * 添加service节点
     * */
    void addService(Service service);

    /**
     * 处理订阅消息发来的连接修改事件
     * */
    void callback(List<ServiceUpdateEvent> events);
}
