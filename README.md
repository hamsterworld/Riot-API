# Riot-API

안녕하세요.


LOL 전적을 가져와 우리팀에서 누가 제일못했는지 판별해보는 간단한 웹사이트 프로젝트입니다.


6/17 
- GameItem을 가져올때 DB튜닝 최적화 완료. (1+N) fetchjoin을 통한 해결
- 기존의 반복되는 insert와 PK를 받아오기위해 반복적인 select호출은성능저하를 일으킴.
       
       insert를 batch하고 strategy_seq에서 allowsize를 늘림으로써반복적인 네트워크 통신을 줄였음.
       기존에 3507ms 3299ms 3335ms = 3380ms 정도의 성능을 보였으나성능개선이후 
       1899ms 1865ms 1816ms = 1860ms 약 55%정도 더 빨라진 성능향상을 보여줬습니다.
     
     
6/18 
- dataDragonGameItemRepository.findByitem를 사용했을경우 기존의 10번의 select를 해야된다는 문제점 발견.
  5경기의 Worst를 찾는다면 5*10번의 select문이 추가로 나가게되는 현상으로 인해 성능 저하우려.
       
       findAll로 gameItem을 가져와서 server에서 직접 조회함으로써 select를 1회만 나가게 하여 성능향상.
       기존 10번의 select의경우 평균 193정도의 시간소요
       그러나 향상이후 평균 3정도의 시간소요로 성능의 98%향상을 보임.
       5000번의 반복실행에도 190시간소요로 좋은 성능향상을 보였습니다.
       
