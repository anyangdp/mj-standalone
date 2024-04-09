import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default {
	socketUrl:  process.env.NODE_ENV === 'development' && process.env.VUE_APP_PROXY === 'true' ? process.env.VUE_APP_WS_BASEURL : process.env.VUE_APP_WS_BASEURL, // ws://localhost:8080/websocket/ws
	reconnecting: false,
	socket: null,
	stompClient: null,
	heartbeat: {
		outgoing: 10000,
		incoming: 0
	},
	initWebsocket: function () {
		/*
			① 创建sockJS对象；
			② 创建stomp客户端
			③ stompClient客户端 连接 stomp服务器
			*/
		this.socket = new SockJS(this.socketUrl)
		this.stompClient = Stomp.over(this.socket)
		console.log("this.socketUrl:", this.socketUrl)
		this.stompClient.connect(
			{}, // headers头部信息。可添加客户端的认证信息。也可以不添加信息，headers 直接就设置为 {}
			// eslint-disable-next-line no-unused-vars
			frame => {
				// 连接成功： 订阅服务器的地址。为了浏览器可以接收到消息，必须先订阅服务器的地址
				console.log("frame:", frame)
				this.connectSucceed()
				// eslint-disable-next-line no-unused-vars
			}, err => {
				// 连接失败的回调
				console.log("err:", err)
				this.reconnect(this.socketUrl, this.connectSucceed)
			})
	},
	connectSucceed: function () {
		// 设置心跳发送接受频率（ms）默认为10000ms。 heart-beating是利用window.setInterval()去规律地发送heart-beats或者检查服务端的heart-beats。
		this.stompClient.heartbeat.outgoing = this.heartbeat.outgoing
		this.stompClient.heartbeat.incoming = this.heartbeat.incoming
		// stompClient.subscribe('/toAll/' + this.form.id, res => {
		// 	console.log("接受报文", res)
		// 	let realData = JSON.parse(res.body)
		// 	console.log("realData", realData)
		// 	if(realData.messageType == 'ONLINE') {
		// 		this.deviceState = {
		// 			state: 'ONLINE',
		// 			timestamp: parseTime(realData.timestamp)
		// 		}
		// 	} else if(realData.messageType == 'OFFLINE') {
		// 		this.deviceState = {
		// 			state: 'OFFLINE',
		// 			timestamp: parseTime(realData.timestamp)
		// 		}
		// 	} else {
		// 		let dd = {}
		// 		for (let key in realData.properties) {
		// 			dd[key] = {
		// 				value: realData.properties[key],
		// 				timestamp: parseTime(realData.timestamp)
		// 			}
		// 		}
		// 		Object.assign(this.realData, dd)
		// 	}
		// 	console.log("this.realData", this.realData)
		// })

		/*
		当客户端与服务端连接成功后，可以调用 send()来发送STOMP消息。这个方法必须有一个参数，用来描述对应的STOMP的目的地。
		另外可以有两个可选的参数：headers，object类型包含额外的信息头部；body，一个String类型的参数。

		client.send("/queue/test", {priority: 9}, "Hello, STOMP");
		// client会发送一个STOMP发送帧给/queue/test，这个帧包含一个设置了priority为9的header和内容为“Hello, STOMP”的body。
		*/
		// this.stompClient.send('/topic/dashboard/send',{}, '1')
	},
	reconnect: function (socketUrl, callback) {
		this.reconnecting = true
		let connected = false
		const timer = setInterval(() => {
			this.socket = new SockJS(socketUrl)
			this.stompClient = Stomp.over(this.socket)
			this.stompClient.connect({}, () => {
				this.reconnecting = false
				connected = true
				clearInterval(timer)
				callback()
			}, err => {
				console.log('Reconnect failed！');
				if(!connected) console.log(err);
				clearInterval(timer)
			})
		}, 1000);
	},
	closeSocket: function () {
		if(this.stompClient != null) {
			this.stompClient.disconnect()
		}
	}
}
