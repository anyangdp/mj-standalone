<template>
	<div :class="['widgets-home', customizing?'customizing':'']" ref="main">
		<div class="widgets-content">
			<div class="widgets-top">
				<div class="widgets-top-title">
					控制台
				</div>
			</div>
			<div class="widgets" ref="widgets">
				<div class="widgets-wrapper">
					<el-row :gutter="15">
						<el-col v-for="(item, index) in grid.layout" v-bind:key="index" :md="item" :xs="24">
							<draggable v-model="grid.copmsList[index]" animation="200" handle=".customize-overlay" group="people" item-key="com" dragClass="aaaaa" force-fallback fallbackOnBody class="draggable-box">
								<template #item="{ element }">
									<div class="widgets-item">
										<component :is="allComps[element]"></component>
									</div>
								</template>
							</draggable>
						</el-col>
					</el-row>
					<el-row :gutter="15">
						<el-col :md="24" :xs="24">
							<div class="widgets-item">
								<component :is="allComps['echarts']"></component>
							</div>
						</el-col>
					</el-row>
					<el-row :gutter="15">
						<el-col :md="24" :xs="24">
							<div class="widgets-item">
								<div id="container"></div>
							</div>
						</el-col>
					</el-row>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import draggable from 'vuedraggable'
	import allComps from './components'
	import AMapLoader from "@amap/amap-jsapi-loader"
	/* eslint-disable */
	import { shallowRef } from '@vue/reactivity'

	export default {
		components: {
			draggable
		},
		data() {
			return {
				customizing: false,
				allComps: allComps,
				selectLayout: [],
				defaultGrid: this.$CONFIG.DEFAULT_GRID,
				grid: []
			}
		},
		created(){
			this.grid = this.$TOOL.data.get("grid") || JSON.parse(JSON.stringify(this.defaultGrid))
		},
		mounted() {
			this.$emit('on-mounted')
			this.initMap();
			this.initDashboard()
		},
		setup() {
			const map = shallowRef(null);
			return{
				map,
			}
		},
		computed: {
			allCompsList(){
				var allCompsList = []
				for(var key in this.allComps){
					allCompsList.push({
						key: key,
						title: allComps[key].title,
						icon: allComps[key].icon,
						description: allComps[key].description
					})
				}
				var myCopmsList = this.grid.copmsList.reduce(function(a, b){return a.concat(b)})
				for(let comp of allCompsList){
					const _item = myCopmsList.find((item)=>{return item === comp.key})
					if(_item){
						comp.disabled = true
					}
				}
				return allCompsList
			},
			myCompsList(){
				return this.allCompsList.filter(item => !item.disabled )
			},
			nowCompsList(){
				return this.grid.copmsList.reduce(function(a, b){return a.concat(b)})
			}
		},
		methods: {
			initMap(){
				AMapLoader.load({
					key:"4bfd8e115ae497e9b2cace3f2779124d",             // 申请好的Web端开发者Key，首次调用 load 时必填
					version:"2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
					plugins:[''],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
				}).then((AMap)=>{
					this.map = new AMap.Map("container",{  //设置地图容器id
						viewMode:"2D",    //是否为3D地图模式
						zoom:5,           //初始化地图级别
						center:[105.602725,37.076636], //初始化地图中心点位置
					});
					const marker = new AMap.Marker({
						position: new AMap.LngLat(116.39, 39.9),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
						title: '北京'
					});
					this.map.add(marker)
				}).catch(e=>{
					console.log(e);
				})
			},
			async initDashboard() {
				// let res = await this.$API.dashboard.dashboard.get()
				// console.log("仪表盘", res);
			}
		}
	}
</script>

<style scoped lang="scss">
	.widgets-home {display: flex;flex-direction: row;flex: 1;height: 100%;}
	.widgets-content {flex: 1;overflow: auto;overflow-x:hidden;padding:15px;}
	.widgets-aside {width: 360px;background: #fff;box-shadow: 0 0 10px rgba(0,0,0,.1);position: relative;overflow: auto;}
	.widgets-aside-title {font-size: 14px;display: flex;align-items: center;justify-content: center;}
	.widgets-aside-title i {margin-right: 10px;font-size: 18px;}
	.widgets-aside-close {font-size: 18px;width:30px;height: 30px;display: flex;align-items: center;justify-content: center;border-radius: 3px;cursor: pointer;}
	.widgets-aside-close:hover {background: rgba(180,180,180,0.1);}

	.widgets-top {margin-bottom: 15px;display: flex;justify-content: space-between;align-items: center;}
	.widgets-top-title {font-size: 18px;font-weight: bold;}

	.widgets {transform-origin: top left;transition: transform .15s;}

	.draggable-box {height: 100%;}

	.customizing .widgets-wrapper {margin-right:-360px}
	.customizing .widgets-wrapper .el-col {padding-bottom:15px;}
	.customizing .widgets-wrapper .draggable-box {border: 1px dashed var(--el-color-primary);padding:15px;}
	.customizing .widgets-wrapper .no-widgets {display: none;}
	.customizing .widgets-item {position: relative;margin-bottom: 15px;}
	.customize-overlay {position: absolute;top:0;right:0;bottom:0;left:0;z-index: 1;display: flex;flex-direction: column;align-items: center;justify-content: center;background: rgba(255,255,255,0.9);cursor: move;}
	.customize-overlay label {background: var(--el-color-primary);color: #fff;height:40px;padding:0 30px;border-radius: 40px;font-size: 18px;display: flex;align-items: center;justify-content: center;cursor: move;}
	.customize-overlay label i {margin-right: 15px;font-size: 24px;}
	.customize-overlay .close {position: absolute;top:15px;right:15px;}
	.customize-overlay .close:focus, .customize-overlay .close:hover {background: var(--el-button-hover-color);}

	.widgets-list {}
	.widgets-list-item {display: flex;flex-direction: row;padding:15px;align-items: center;}
	.widgets-list-item .item-logo {width: 40px;height: 40px;border-radius: 50%;background: rgba(180,180,180,0.1);display: flex;align-items: center;justify-content: center;font-size: 18px;margin-right: 15px;color: #6a8bad;}
	.widgets-list-item .item-info {flex: 1;}
	.widgets-list-item .item-info h2 {font-size: 16px;font-weight: normal;cursor: default;}
	.widgets-list-item .item-info p {font-size: 12px;color: #999;cursor: default;}
	.widgets-list-item:hover {background: rgba(180,180,180,0.1);}

	.widgets-wrapper .sortable-ghost {opacity: 0.5;}

	.selectLayout {width: 100%;display: flex;}
	.selectLayout-item {width:60px;height:60px;border: 2px solid var(--el-border-color-light);padding:5px;cursor: pointer;margin-right: 15px;}
	.selectLayout-item span {display: block;background: var(--el-border-color-light);height:46px;}
	.selectLayout-item.item02 span {height:30px;}
	.selectLayout-item.item02 .el-col:nth-child(1) span {height:14px;margin-bottom: 2px;}
	.selectLayout-item.item03 span {height:14px;margin-bottom: 2px;}
	.selectLayout-item:hover {border-color: var(--el-color-primary);}
	.selectLayout-item.active {border-color: var(--el-color-primary);}
	.selectLayout-item.active span {background: var(--el-color-primary);}

	#container{
		padding:0px;
		margin: 0px;
		width: 100%;
		height: 800px;
	}

	.dark {
		.widgets-aside {background: #2b2b2b;}
		.customize-overlay {background: rgba(43,43,43,0.9);}
	}

	@media (max-width: 992px){
		.customizing .widgets {transform: scale(1) !important;}
		.customizing .widgets-aside {width: 100%;position: absolute;top:50%;right:0;bottom:0;}
		.customizing .widgets-wrapper {margin-right:0;}
	}

</style>
