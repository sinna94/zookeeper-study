package chung.me.zookeeperstudy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class ZookeeperStudyApplication

fun main(args: Array<String>) {
    runApplication<ZookeeperStudyApplication>(*args)
}
