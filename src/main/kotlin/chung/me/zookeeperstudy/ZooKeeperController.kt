package chung.me.zookeeperstudy

import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("zoo-keeper")
class ZooKeeperController(
    private val discoveryClient: DiscoveryClient,
    private val serviceRegistry: ZookeeperServiceRegistry,
    private val zookeeperListener: ZookeeperListener,
    private val loadBalancer: LoadBalancerClient,
){

    @GetMapping
    fun getServiceInstance(): ServiceInstance? {
        val instances = discoveryClient.getInstances("ZooKeeperTest")
        if (instances.isNullOrEmpty()) {
            return null
        }
        return instances[0]
    }

    @GetMapping("lb")
    fun getServiceInstanceUsingLB(): ServiceInstance {
        return loadBalancer.choose("ZooKeeperTest")
    }

    @GetMapping("listener")
    fun getListener(): MutableSet<String> {
        return zookeeperListener.getDependencies()
    }

    @GetMapping("anotherService")
    fun getAnotherServiceInstance(): ServiceInstance? {
        val instances = discoveryClient.getInstances("test/anotherService")
        if (instances.isNullOrEmpty()) {
            return null
        }
        return instances[0]
    }

    @GetMapping("url")
    fun serviceUrl(): String? {
        val instances = discoveryClient.getInstances("ZooKeeperTest")
        if (instances.isNullOrEmpty()) {
            return null
        }
        return instances[0].uri.toString()
    }

    @PostMapping
    fun register() {
        ServiceInstanceRegistration.builder().defaultUriSpec().address("/test/url").port(9000)
            .name("/test/anotherService").build().let {
            serviceRegistry.register(it)
        }
    }
}
