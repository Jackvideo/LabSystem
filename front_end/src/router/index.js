import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '主页', icon: 'dashboard' }
    }]
  },

  {
    path: '/department',
    component: Layout,
    redirect: '/department/main',
    name: 'Department',
    meta: { title: '单位模块', icon: 'el-icon-office-building' },
    children: [
      {
        path: 'space',
        name: 'Space',
        component: () => import('@/views/search/space'),
        meta: { title: '办公场地', icon: 'el-icon-office-building' }
      },
      {
        path: 'lab',
        name: 'Lab',
        component: () => import('@/views/search/lab'),
        meta: { title: '研究室', icon: 'el-icon-school' }
      },
      {
        path: 'department',
        name: 'Department',
        component: () => import('@/views/search/department'),
        meta: { title: '三方单位', icon: 'el-icon-menu' }
      },
      {
        path: 'contactrelation',
        name: 'Contactrelation',
        component: () => import('@/views/search/contactrelation'),
        meta: { title: '联系人记录', icon: 'el-icon-notebook-1' }
      }
    ]
  },
  {
    path: '/worker',
    component: Layout,
    redirect: '/worker/main',
    name: 'Worker',
    meta: { title: '人员模块', icon: 'el-icon-user-solid' },
    children: [
      {
        path: 'researcher',
        name: 'Researchers',
        component: () => import('@/views/search/labers'),
        meta: { title: '研究人员', icon: 'el-icon-user' }
      },
      {
        path: 'worker',
        name: 'Worker',
        component: () => import('@/views/search/worker'),
        meta: { title: '工作人员', icon: 'el-icon-user-solid' }
      }
    ]
  },


  {
    path: '/project',
    component: Layout,
    redirect: '/project/main',
    name: 'Project',
    meta: { title: '项目模块', icon: 'table' },
    children: [
      {
        path: 'project',
        name: 'Project',
        component: () => import('@/views/search/project'),
        meta: { title: '科研项目', icon: 'table' }
      },
      {
        path: 'pdrelation',
        name: 'Pdrelation',
        component: () => import('@/views/search/pdrelation'),
        meta: { title: '项目单位联系', icon: 'table' }
      },
      {
        path: 'outcome',
        name: 'Outcome',
        component: () => import('@/views/search/outcome'),
        meta: { title: '科研成果', icon: 'el-icon-star-off' },
      },
      {
        path: 'contributor',
        name: 'Contributor',
        component: () => import('@/views/search/contributor'),
        meta: { title: '贡献记录', icon: 'el-icon-notebook-2' }
      },
      {
        path: 'record',
        name: 'Record',
        component: () => import('@/views/search/record'),
        meta: { title: '研究人员参与记录', icon: 'el-icon-notebook-1' }
      }
    ]
  },

  {
    path: '/else',
    component: Layout,
    redirect: '/else/main',
    name: 'Else',
    meta: { title: '其他模块', icon: 'el-icon-s-help' },
    children: [

    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
