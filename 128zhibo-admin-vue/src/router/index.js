import Vue from 'vue'
import Router from 'vue-router'
import Header from '@/components/Header'
import Footer from '@/components/Footer'
import Login from '@/components/Login'
import Menu from '@/components/menu'
import Code from '@/components/admin/Code'
//import ResetPassword from '@/components/admin/resetPassword'
import LiveSourceList from '@/components/admin/LiveSourceList'
import LiveSourceForm from '@/components/admin/LiveSourceForm'
import MatchList from '@/components/admin/MatchList'
import MatchForm from '@/components/admin/MatchForm'
//import VideoList from '@/components/admin/VideoList'
//import VideoForm from '@/components/admin/VideoForm'
import FriendLinkList from '@/components/admin/FriendLinkList'
import FriendLinkForm from '@/components/admin/FriendLinkForm'
//import NewsList from '@/components/admin/NewsList'
//import NewsForm from '@/components/admin/NewsForm'
//import ImageList from '@/components/admin/ImageList'
//import ImageForm from '@/components/admin/ImageForm'
//import TeleList from '@/components/admin/TeleList'
//import TeleForm from '@/components/admin/TeleForm'
import PageAdList from '@/components/admin/PageAdList'
import PageAdForm from '@/components/admin/PageAdForm'
//import AdList from '@/components/admin/AdList'
//import AdForm from '@/components/admin/AdForm'
import AccountList from '@/components/admin/AccountList'
import AccountForm from '@/components/admin/AccountForm'
import LogList from '@/components/admin/LogList'
//import ReplaceWordList from '@/components/admin/ReplaceWordList'
//import ReplaceWordForm from '@/components/admin/ReplaceWordForm'
//import ConfigList from '@/components/admin/ConfigList'
//import ConfigForm from '@/components/admin/ConfigForm'
import TeamList from '@/components/admin/TeamList'
import TeamForm from '@/components/admin/TeamForm'
import LeagueList from '@/components/admin/LeagueList'
import LeagueForm from '@/components/admin/LeagueForm'
//import SensitiveList from '@/components/admin/SensitiveList'
//import SensitiveForm from '@/components/admin/SensitiveForm'
//import IssueList from '@/components/admin/IssueList'
//import IssueForm from '@/components/admin/IssueForm'
//import UserForm from '@/components/admin/UserForm'
//import AddIssueForm from '@/components/admin/AddIssueForm'
//import UpdateIssueForm from '@/components/admin/UpdateIssueForm.vue'
//import ProblemDbList from '@/components/admin/ProblemDbList'
//import ProblemDbForm from '@/components/admin/ProblemDbForm'

Vue.use(Router)

const router = new Router({
  routes: [

    {
      path: '/login',
      name: 'Login',
      components: {
        'login': Login
      }
    },
    {
      path: '/code',
      name: 'Code',
      components: {
        'header': Header,
        'menu': Menu,
        'default': Code,
        'footer': Footer
      }
    },
    {
      path: '/liveSourceList',
      name: 'LiveSourceList',
      components: {
        'header': Header,
        'menu': Menu,
        'default': LiveSourceList,
        'footer': Footer
      }
    },
    {
      path: '/liveSourceForm',
      name: 'LiveSourceCreateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': LiveSourceForm,
        'footer': Footer
      }
    },
    {
      path: '/liveSourceForm/:id',
      name: 'LiveSourceUpdateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': LiveSourceForm,
        'footer': Footer
      }
    },
    {
      path: '/matchList',
      name: 'MatchList',
      components: {
        'header': Header,
        'menu': Menu,
        'default': MatchList,
        'footer': Footer
      }
    },
    {
      path: '/matchForm',
      name: 'MatchCreateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': MatchForm,
        'footer': Footer
      }
    },
    {
      path: '/matchForm/:id',
      name: 'MatchUpdateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': MatchForm,
        'footer': Footer
      }
   },
    //{
    //  path: '/videoList',
    //  name: 'VideoList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': VideoList,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/videoForm',
    //  name: 'VideoCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': VideoForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/videoForm/:id',
    //  name: 'VideoUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': VideoForm,
    //    'footer': Footer
    //  }
    //},
    {
      path: '/friendLinkList',
      name: 'FriendLinkList',
      components: {
        'header': Header,
        'menu': Menu,
        'default': FriendLinkList,
        'footer': Footer
      }
    },
    {
      path: '/friendLinkForm',
      name: 'FriendLinkCreateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': FriendLinkForm,
        'footer': Footer
      }
    },
    {
      path: '/friendLinkForm/:id',
      name: 'FriendLinkUpdateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': FriendLinkForm,
        'footer': Footer
      }
    },
    //{
    //  path: '/newsList',
    //  name: 'NewsList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': NewsList,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/newsForm',
    //  name: 'NewsCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': NewsForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/newsForm/:id',
    //  name: 'NewsUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': NewsForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/imageList',
    //  name: 'ImageList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ImageList,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/imageForm',
    //  name: 'ImageCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ImageForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/imageForm/:id',
    //  name: 'ImageUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ImageForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/teleList',
    //  name: 'TeleList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': TeleList,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/teleForm',
    //  name: 'TeleCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': TeleForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/teleForm/:id',
    //  name: 'TeleUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': TeleForm,
    //    'footer': Footer
    //  }
    //},
    {
      path: '/pageAdList',
      name: 'PageAdList',
      components: {
        'header': Header,
        'menu': Menu,
        'default': PageAdList,
        'footer': Footer
      }
    },
    {
      path: '/pageAdForm',
      name: 'PageAdCreateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': PageAdForm,
        'footer': Footer
      }
    },
    {
      path: '/pageAdForm/:id',
      name: 'PageAdUpdateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': PageAdForm,
        'footer': Footer
      }
    },
    //{
    //  path: '/adList',
    //  name: 'AdList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': AdList,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/adForm',
    //  name: 'AdCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': AdForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/adForm/:id',
    //  name: 'AdUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': AdForm,
    //    'footer': Footer
    //  }
    //},
    {
      path: '/accountList',
      name: 'AccountList',
      components: {
        'header': Header,
        'menu': Menu,
        'default': AccountList,
        'footer': Footer
      }
    },
    {
      path: '/accountForm',
      name: 'AccountCreateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': AccountForm,
        'footer': Footer
      }
    },
    {
      path: '/accountForm/:id',
      name: 'AccountUpdateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': AccountForm,
        'footer': Footer
      }
    },
    {
      path: '/logList',
      name: 'LogList',
      components: {
        'header': Header,
        'menu': Menu,
        'default': LogList,
        'footer': Footer
      }
    },
    //{
    //  path: '/replaceWordList',
    //  name: 'ReplaceWordList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ReplaceWordList,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/replaceWordForm',
    //  name: 'ReplaceWordCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ReplaceWordForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/replaceWordForm/:id',
    //  name: 'ReplaceWordUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ReplaceWordForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/resetPassword',
    //  name: 'ResetPassword',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ResetPassword,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/configList',
    //  name: 'ConfigList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ConfigList,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/configForm',
    //  name: 'ConfigCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ConfigForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/configForm/:id',
    //  name: 'ConfigUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ConfigForm,
    //    'footer': Footer
    //  }
    //},
    {
      path: '/teamList',
      name: 'TeamList',
      components: {
        'header': Header,
        'menu': Menu,
        'default': TeamList,
        'footer': Footer
      }
    },{
      path: '/teamForm',
      name: 'TeamCreateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': TeamForm,
        'footer': Footer
      }
    },
    {
      path: '/teamForm/:id',
      name: 'TeamUpdateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': TeamForm,
        'footer': Footer
      }
    }, {
      path: '/leagueList',
      name: 'LeagueList',
      components: {
        'header': Header,
        'menu': Menu,
        'default': LeagueList,
        'footer': Footer
      }
    },{
      path: '/leagueForm',
      name: 'TeagueCreateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': LeagueForm,
        'footer': Footer
      }
    },
    {
      path: '/leagueForm/:id',
      name: 'LeagueUpdateForm',
      components: {
        'header': Header,
        'menu': Menu,
        'default': LeagueForm,
        'footer': Footer
      }
    }
    //,
    //{
    //  path: '/sensitiveList',
    //  name: 'SensitiveList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': SensitiveList,
    //    'footer': Footer
    //  }
    //},{
    //  path: '/sensitiveForm',
    //  name: 'SensitiveCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': SensitiveForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/sensitiveForm/:id',
    //  name: 'SensitiveUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': SensitiveForm,
    //    'footer': Footer
    //  }
    //},{
    //  path: '/issueList',
    //  name: 'IssueList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': IssueList,
    //    'footer': Footer
    //  }
    //},{
    //  path: '/issueForm',
    //  name: 'IssueCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': IssueForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/userForm/:id',
    //  name: 'userForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': UserForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/issueForm/:id',
    //  name: 'IssueUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': IssueForm,
    //    'footer': Footer
    //  }
    //},{
    //  path: '/updateIssueForm/:id',
    //  name: 'newUpdateIssueForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': UpdateIssueForm,
    //    'footer': Footer
    //  }
    //},{
    //  path: '/addIssueForm',
    //  name: 'AddCreateIssueForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': AddIssueForm,
    //    'footer': Footer
    //  }
    //},{
    //  path: '/problemDbList',
    //  name: 'ProblemDbList',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ProblemDbList,
    //    'footer': Footer
    //  }
    //},{
    //  path: '/problemDbForm',
    //  name: 'ProblemDbCreateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ProblemDbForm,
    //    'footer': Footer
    //  }
    //},
    //{
    //  path: '/problemDbForm/:id',
    //  name: 'ProblemDbUpdateForm',
    //  components: {
    //    'header': Header,
    //    'menu': Menu,
    //    'default': ProblemDbForm,
    //    'footer': Footer
    //  }
    //}
  ]
})

window.router = router

export default router
