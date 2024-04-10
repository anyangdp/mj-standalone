<template>

	<el-card shadow="never">
		<sc-statistic title="用户" :value="count" suffix="个" groupSeparator>
			<sc-trend v-model="trend"></sc-trend>
		</sc-statistic>
	</el-card>

</template>

<script>
import scStatistic from '@/components/scStatistic';

export default {
	name: "visitor-dashboard",
	title: "用户",
	icon: "el-icon-clock",
	description: "用户",
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

		},
	}
}
</script>

<style scoped>

</style>
