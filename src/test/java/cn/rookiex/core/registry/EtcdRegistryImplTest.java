package cn.rookiex.core.registry;

import cn.rookiex.core.center.EtcdRegisterCenterImpl;
import cn.rookiex.core.factory.EtcdServiceFactoryImpl;
import cn.rookiex.core.service.Service;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author : Rookiex
 * @Date : 2019/07/05
 * @Describe :
 */
@SuppressWarnings("Duplicates")
public class EtcdRegistryImplTest {
    private Logger log = Logger.getLogger(getClass());
    private String endpoint = "http://etcd.rookiex.cn:2379";
    private String service = "testService";
    private String ip = "192.168.2.26";
    private int port = 1111;

    @BeforeClass
    public static void before() {
        EtcdRegisterCenterImpl.setFactory(new EtcdServiceFactoryImpl());
    }

    @Test
    public void init() {
    }

    @Test
    public void getServiceList() {
    }

    @Test
    public void registerService() {
        int size = 5;
        EtcdRegistryImpl etcdRegister = new EtcdRegistryImpl();
        etcdRegister.init(endpoint);

        List<Service> serviceList = etcdRegister.getServiceList(service);
        System.out.println("before start");
        serviceList.forEach(service -> {
            String serviceName = service.getServiceName();
            String fullPath = service.getFullPath();
            System.out.println("before path ==> " + fullPath + " ,name ==> " + serviceName + " serverIsBand ==> " + service.isBanned());
        });
        System.out.println("before over");

        try {
            for (int i = 0; i < size; i++) {
                String newIp = ip + ":" + (port + i);
                etcdRegister.registerService(service, newIp);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        serviceList = etcdRegister.getServiceList(service);
        System.out.println("after start");
        serviceList.forEach(service -> {
            String serviceName = service.getServiceName();
            String fullPath = service.getFullPath();
            System.out.println("after path ==> " + fullPath + " ,name ==> " + serviceName + " serverIsBand ==> " + service.isBanned());
        });
        System.out.println("after over");

    }

    @Test
    public void registerServiceKeepAlive() {
        int size = 1;
        EtcdRegistryImpl etcdRegister = new EtcdRegistryImpl();
        etcdRegister.init(endpoint);

        List<Service> serviceList;
        try {
            for (int i = 0; i < size; i++) {
                String newIp = ip + ":" + (port + i);
                etcdRegister.registerService(service, newIp);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serviceList = etcdRegister.getServiceList(service);
            log.info("after start");
            serviceList.forEach(service -> {
                String serviceName = service.getServiceName();
                String fullPath = service.getFullPath();
                log.info("after path ==> " + fullPath + " ,name ==> " + serviceName + " serverIsBand ==> " + service.isBanned());
            });
            log.info("after over");
        }
    }

    @Test
    public void bandService() {
        int size = 5;
        EtcdRegistryImpl etcdRegister = new EtcdRegistryImpl();
        etcdRegister.init(endpoint);
        try {
            for (int i = 0; i < size; i++) {
                String newIp = ip + ":" + (port + i);
                etcdRegister.registerService(service, newIp);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        List<Service> serviceList = etcdRegister.getServiceList(service);
        System.out.println("after start");
        serviceList.forEach(service -> {
            String serviceName = service.getServiceName();
            String fullPath = service.getFullPath();
            System.out.println("registry path ==> " + fullPath + " ,name ==> " + serviceName + " serverIsBand ==> " + service.isBanned());
        });
        System.out.println("after over");

        try {
            for (int i = 0; i < size; i++) {
                String newIp = ip + ":" + (port + i);
                etcdRegister.bandService(service, newIp);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        serviceList = etcdRegister.getServiceList(service);
        System.out.println("band start");
        serviceList.forEach(service -> {
            String serviceName = service.getServiceName();
            String fullPath = service.getFullPath();
            System.out.println("band path ==> " + fullPath + " ,name ==> " + serviceName + " serverIsBand ==> " + service.isBanned());
        });
        System.out.println("band over");
    }

    @Test
    public void watch() {
    }

    @Test
    public void unWatch() {
    }
}