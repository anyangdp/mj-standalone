<template>

	<el-card shadow="never">
		<sc-statistic title="累计支持协议" :value="count" suffix="个" groupSeparator>
			<sc-trend v-model="trend"></sc-trend>
		</sc-statistic>
	</el-card>
</template>

<script>
import scStatistic from '@/components/scStatistic';

export default {
	name: "device-dashboard",
	title: "协议",
	icon: "el-icon-clock",
	description: "协议模块",
	components: {
		scStatistic
	},
	data() {
		return {
			count: 0,
			trend: 0
		}
	},
	mounted() {
		this.initDashboard()
	},
	methods: {
		async initDashboard() {
			let res = await this.$API.dashboard.protocol.get()
			this.count = res.count
			this.trend = res.trend
			console.log("仪表盘", res);
		},
	}
}
</script>

<style scoped>

</style>
