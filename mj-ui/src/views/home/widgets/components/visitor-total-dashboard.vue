<template>
	<el-card shadow="never">
		<sc-statistic title="总访客" :value="count" suffix="个" groupSeparator>
			<sc-trend v-model="trend"></sc-trend>
		</sc-statistic>
	</el-card>
</template>

<script>
import scStatistic from '@/components/scStatistic';
export default {
	name: "visitor-total-dashboard",
	title: "总访客",
	icon: "el-icon-clock",
	description: "总访客",
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
