<template>

	<el-card shadow="never">
		<sc-statistic title="累计激活设备数" :value="count" suffix="个" groupSeparator>
			<sc-trend v-model="trend"></sc-trend>
		</sc-statistic>
	</el-card>

</template>

<script>
import scStatistic from '@/components/scStatistic';

export default {
	name: "device-dashboard",
	title: "时钟",
	icon: "el-icon-clock",
	description: "演示部件效果",
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
			let res = await this.$API.dashboard.device.get()
			this.count = res.count
			this.trend = res.trend
		},
	}
}
</script>

<style scoped>

</style>
