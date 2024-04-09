<template>
	<el-container>
		<el-container>
			<el-main class="nopadding">
				<el-container>
					<el-header>
						<div class="left-panel">
							<el-date-picker v-model="date" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
						</div>
						<div class="right-panel">

						</div>
					</el-header>
					<el-main class="nopadding">
						<scTable ref="table" :apiObj="apiObj" stripe highlightCurrentRow @row-click="rowClick">
							<el-table-column label="ID" prop="id" width="180"></el-table-column>
							<el-table-column label="日志名" prop="name" width="150"></el-table-column>
							<el-table-column label="请求接口" prop="url" width="150"></el-table-column>
							<el-table-column label="请求时间" prop="time" width="170"></el-table-column>
							<el-table-column label="用户" prop="user" width="150"></el-table-column>
							<el-table-column label="客户端IP" prop="cip" width="150"></el-table-column>
							<el-table-column label="耗时" prop="timeConsumed" width="150"></el-table-column>
						</scTable>
					</el-main>
				</el-container>
			</el-main>
		</el-container>
	</el-container>

	<el-drawer v-model="infoDrawer" title="日志详情" :size="600" destroy-on-close>
		<info ref="info"></info>
	</el-drawer>
</template>

<script>
	import info from './info'

	export default {
		name: 'log',
		components: {
			info
		},
		data() {
			return {
				infoDrawer: false,
				date: [],
				apiObj: this.$API.system.log.list,
				search: {
					keyword: ""
				}
			}
		},
		methods: {
			upsearch(){

			},
			rowClick(row){
				this.infoDrawer = true
				this.$nextTick(() => {
					this.$refs.info.setData(row)
				})
			}
		}
	}
</script>

<style>
</style>
