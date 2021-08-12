# log-manager (현재 진행 중)⛄️
kafka를 이용한 비동기 로그 저장 라이브러리(현재 진행중)  
토이 프로젝트에 가져다 쓰려고 만드는 라이브러리  
<BR>


# 아키텍처
![Untitled Diagram](https://user-images.githubusercontent.com/50160282/128188817-7f885e70-c5fc-4d5a-84c7-f28cc5e55f96.png)

## Common properties
|name|value|mandatory|editable|default|description|
|---|---|---|---|---|---|
|logmanager.async.core.pool|ex)5|X|O|5|thread pool core pool size|
|logmanager.async.max.pool.size|ex)10|X|O|10|thread pool max pool size|
|logmanager.async.queue.capacity|ex)325324|X|O|2147483647|이 수 만큼의 작업이 Queue에 쌓이면 pool size를 1씩 증가|
## producer properties
|name|value|mandatory|editable|default|description|
|---|---|---|---|---|---|
|logmanager.bootstrap.servers|ex)192.169.2.1:9092, 192.169.2.2:9092|O|O|none|전송하려는 Cluster에 속한 브로커들 주소|
|logmanager.kafka.producer.acks|1|X|O|1|리더 파티션의로부터의 응답만 확인함|
|logmanager.kafka.producer.compression.type|gzip|X|O|none|압축방식.gzip은 준수한 처리율, 높은 압축률|
|logmanager.kafka.batch.size.config|ex)100000|X|O|16384|지정한 크기만큼 메세지가 쌓이면 전송|
|logmanager.kafka.buffer.memory.config|ex)33554432(32MB)|X|O|32MB|아직 브로커로 전송되지 않은 메세지들을 저장하는 공간의 크기|
|logmanager.kafka.linger.ms.config|ex)5000(5s)|X|X|0|batch.size.config만 메세지가 쌓이지 않아도, 이 기간만 지나면 메세지 전송. (너무 짧으면 batch하는 의미가 없고, 너무 길면 리소스 상황이 충분히 여유 있음에도 불구하고 전송하지 않고 기다릴 수 있어서 적당한 시간 선택 중요 )|
|logmanager.kafka.max.block.ms.config|ex)2000|X|X|60000|buffer가 가득 찼을 때, send(), partitionsFor()을 블락하는 시간. 이 시간이 지나고 buffer.memory가 안 비워지면 에러를 발생.|
