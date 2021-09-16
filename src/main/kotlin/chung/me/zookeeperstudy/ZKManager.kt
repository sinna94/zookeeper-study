package chung.me.zookeeperstudy

interface ZKManager {
    fun create(path: String, data: ByteArray)
    fun getZNodeData(path: String, watchFlag: Boolean): Any
    fun update(path: String, data: ByteArray)
}
