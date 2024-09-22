package pt.isel.daw.tictactoe.http

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress

@RestController
class StatusController {
    @GetMapping(Uris.Status.HOSTNAME)
    fun getStatusHostname(): String = System.getenv("HOSTNAME")

    @GetMapping(Uris.Status.IP)
    fun getStatusIp(): String = InetAddress.getLocalHost().hostAddress
}