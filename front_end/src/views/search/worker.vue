<template>
    <div>
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="研究室主任" name="1"></el-tab-pane>
            <el-tab-pane label="负责人" name="2"></el-tab-pane>
            <el-tab-pane label="联系人" name="3"></el-tab-pane>
            <el-tab-pane label="秘书" name="4"></el-tab-pane>

            <div class="tab-content">
                <keep-alive>
                    <component :is="curTabcompnents[activeName]"></component>
                </keep-alive>
            </div>
        </el-tabs>
    </div>
</template>

<script>

import CommonApi from '@/api/back_end'
import Leader from './workers/leader.vue'
import Director from './workers/director.vue'
import Secretary from './workers/secretary.vue';
import Contact from './workers/contact.vue'
export default {
    data() {
        return {
            activeName: '1',
            curTabcompnents: {
                '1': 'Director',
                '2': 'Leader',
                '3': 'Contact',
                '4': 'Secretary',
            }
        }
    },
    components: { Leader, Director, Secretary, Contact },
    methods: {
        handleClick(tab) {
            this.activeName = tab.name;
        }
    }
};

</script>