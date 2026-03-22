## Section 1 – Introduction（约 0:00–1:30）

**1.1 自我介绍 + 课程 + 项目标题**

My name is **Yang Ziyi**, and this is my video for the module **AT3**.  
我叫 **[杨子毅]**，这是我为 **COM668 软件工程** 课程准备的演示视频。

The title of my project is **“Design and Development of a Web-Based Platform for Real-Time Competitive Snake AI Battles”**  
（中文标题：**“基于 Spring Boot 和 Vue 的在线贪吃蛇对战平台”**）。

This project aims to build a **real‑time web platform** where players can either control a snake manually or write **AI scripts** to fight automatically against other players or bots, with a built‑in **rating system** and **game replay**.  
这个项目的目标，是搭建一个支持实时对战的网页平台，玩家既可以手动操控贪吃蛇，也可以编写 **AI 脚本** 自动对战，并通过 **评分系统** 和 **对局回放** 来分析表现。

- `web application (网页应用)`  
- `bot (机器人程序，用代码控制的玩家)`

---

**1.2 项目要解决的问题与目标**

In my AT1 and AT2 reports, I defined the problem as the lack of **deep, long‑term engagement** in existing online snake games, and the need for a platform that combines **real‑time multiplayer battles** with **programmable AI agents**.  
在 AT1 和 AT2 中，我把问题定义为：现有在线贪吃蛇游戏缺乏长期的深度玩法，需要一个把 **实时多人对战** 和 **可编程 AI 对战** 结合起来的平台。

The aim of this project is therefore to design and develop a **real‑time competitive snake AI platform** that supports low‑latency battles via WebSocket, a secure **sandbox** for user‑submitted AI code, and a **skill‑based rating and matchmaking system**.  
因此，本项目的目标是设计并开发一个 **实时贪吃蛇 AI 对战平台**，通过 WebSocket 提供低延迟对战，使用安全的 **沙箱环境 (sandbox)** 运行用户提交的 AI 代码，并提供基于技能的 **评分和匹配系统**。

Concretely, the system should allow users to register, log in, play manual matches, manage AI bots, view rankings, and replay past games, while meeting key non‑functional requirements such as **performance, security, and usability**.  
更具体地说，系统需要支持注册登录、手动对战、Bot 管理、排行榜和对战回放，并在此基础上满足 **性能、安全性和可用性** 等非功能需求。

---

**1.3 完成度简单评价**

In terms of how far I have met this aim, the **core workflow is complete**: the platform supports user registration and login, real‑time online battles, a basic rating‑based **Ranklist**, match records with replay, and user‑managed bots that can participate in games.  
就目标达成度来说，**核心流程已经完成**：平台支持注册登录、实时在线对战、基于评分的排行榜、带回放的对战记录，以及可以参与对战的用户自定义 Bot。

However, some aspects from the original plan are **simplified** in this implementation: for example, the rating system is inspired by ELO but not as sophisticated as the full design in AT2, and the sandbox for AI scripts focuses on safety and stability over supporting multiple languages.  
但和最初的设计相比，有些部分在实现中做了 **适当简化**：例如，当前的评分系统参考了 ELO 思路，但还没有完全实现 AT2 中规划的全部细节；Bot 沙箱环境也优先保证安全与稳定，而不是支持多种语言。

Overall, the software is stable enough to demonstrate **real‑time matches, AI vs AI/AI vs human battles, and end‑to‑end data flow** from gameplay to persistent storage and replay, which addresses the main technical challenges defined in AT1 and AT2.  
总体来说，系统已经足够稳定，可以完整演示 **实时对战、AI 对战和端到端数据流**（从对战到持久化和回放），基本解决了 AT1 和 AT2 中提出的主要技术挑战。

---

## Section 2 – Software Demonstration（约 1:30–7:30）

> 这里按“当前页面 + 操作 + 口播”来写，你录制时边点边读。

---

### 2.1 用户注册 / 登录（页面：`UserAccountRegisterView` → `UserAccountLoginView`）（约 1:30–3:00）

**在浏览器上：打开 `UserAccountRegisterView` 注册页**

Now I will start with the **user registration and login** features, which implement the **user management** requirements defined in AT2.  
现在我先演示一下系统的 **用户注册和登录** 功能，它对应 AT2 中的软件定义里关于 **用户管理** 的需求。

On this page, a new user can create an account by entering a username, a password, and confirming the password.  
在这个页面，新的用户可以通过输入用户名、密码和确认密码来创建账号。

- `confirm password (确认密码)`

The form validates the inputs and shows error messages when something is wrong, demonstrating both **correctness** and **robustness** as required by the AT3 brief.  
表单会校验输入内容，如果有问题，会给出错误提示，这里体现的是 AT3 要求的 **正确性** 和 **健壮性**。

---

**操作：先演示错误场景，例如两次密码不一致**

For example, if the two passwords do not match, the system will reject the registration and show a clear error message.  
比如，当两次输入的密码不一致时，系统会拒绝注册，并显示清晰的错误提示信息。

This demonstrates the **correctness** and **robustness (健壮性)** of the input validation, including how the system handles boundary and invalid inputs.  
这展示了输入校验的 **正确性** 和 **健壮性**，包括系统是如何处理边界输入和非法输入的。

---

**操作：输入一个合法的新账号并提交注册**

Now I enter a valid username and a strong password, and then click the register button.  
现在我输入一个合法的用户名和一个足够强的密码，然后点击注册按钮。

If the registration is successful, the system will create the user in the backend and redirect me to the login page.  
如果注册成功，系统会在后端创建这个用户，并把我跳转到登录页面。

This flow proves that the main registration path works correctly from the frontend to the backend.  
这个流程证明了从前端到后端的注册主流程是正常工作的。

---

**切换到 `UserAccountLoginView` 登录页**

Here is the **login page**.  
这里是 **登录页面**。

The user can log in with the username and password that we just registered.  
用户可以用刚刚注册的用户名和密码登录。

The password is not stored in plain text; it is encoded in the backend using a password encoder.  
密码在后端不会以明文存储，而是通过密码编码器进行加密存储。

- `plain text password (明文密码)`  
- `password encoder (密码编码器)`

---

**操作：先演示输错密码，再演示正确登录**

If I type a wrong password, the server will return an error, and the page will show that the username or password is incorrect, again evidencing correct handling of invalid credentials.  
如果我输入错误的密码，服务器会返回错误信息，页面会提示“用户名或密码错误”，再次说明系统能正确处理无效的登录凭证。

Now I enter the correct password and log in.  
现在我输入正确的密码并登录。

After successful login, the backend generates a **JWT token** and returns it to the frontend.  
登录成功后，后端会生成一个 **JWT token** 并返回给前端。

The token is stored in the browser and will be attached to future API requests for authentication, which implements the **secure authentication and authorization** objective from AT2.  
这个 token 会保存在浏览器中，并会附加到之后的 API 请求里用于身份认证，对应 AT2 中提出的 **安全认证和授权** 目标。

- `JWT token (JSON Web Token，一种无状态的认证令牌)`

---

### 2.2 主页 / 导航栏结构（页面：主页面 + `NavBar`）（约 3:00–3:40）

After logging in, we can see the **main navigation bar** at the top of the page.  
登录之后，我们可以看到页面顶部的 **主导航栏**。

The navigation bar includes several menu items: **PK**, **Ranklist**, **Record**, **Bot**, and **Userinfo**. Together they map to the main functional requirements from AT2: real‑time gameplay, rating and matchmaking, replay, AI management, and user profile.  
导航栏里有几个菜单项：**PK**、**Ranklist**、**Record**、**Bot** 和 **Userinfo**，基本对应 AT2 里定义的主要功能需求：实时对战、评分和匹配、对局回放、AI 管理和用户资料。

- `navigation bar (导航栏)`  
- `user profile (用户资料/用户信息)`

---

### 2.3 匹配对战 / PK（页面：`PKindexView` + `MatchGround` + `ResultBoard`）（约 3:40–5:20）

**切换到 `PKindexView` 对战首页**

Next, I will demonstrate the **online match** feature, which is the core of the **real‑time multiplayer snake battle arena** described in AT1 and AT2.  
接下来，我演示一下 **在线对战** 功能，它是 AT1 和 AT2 中描述的 **实时多人贪吃蛇对战竞技场** 的核心部分。

This is the PK page. In the center, we have the **MatchGround** component, which shows the game map and the snakes.  
这里是 PK 页面，中间是 **MatchGround** 组件，用来展示游戏地图和每条蛇。

On the right side, we have the **ResultBoard**, which will show the final result after the game finishes.  
右侧是 **ResultBoard** 结果面板，在对局结束后会显示最终结果。

---

**操作：点击“开始匹配”或“开始游戏”按钮，等待匹配成功**

When I click the **Start Match** button, the frontend sends a request to the backend to join a match‑making queue.  
当我点击“开始匹配”按钮时，前端会向后端发送请求，让当前玩家加入一个匹配队列。

The backend pairs two players or a player and a bot based on their current rating, and then starts a new game room.  
后端会根据当前的评分把两名玩家，或者玩家和一个 Bot 进行配对，然后创建一个新的游戏房间。

Once the match is created, the game state is pushed to the browser via **WebSocket**, and the snakes start to move step by step, which evidences the low‑latency real‑time communication discussed in AT2.  
一旦匹配成功，游戏状态就会通过 **WebSocket** 持续推送到浏览器，蛇会一回合一回合地移动，这展示了 AT2 中讨论的低延迟实时通信。

- `match‑making queue (匹配队列)`  
- `game state (游戏状态)`

---

**口述规则与控制方式**

In each round, both players – or their bots – choose a direction for their snake, and the game engine updates the positions on the map and checks for collisions.  
在每一回合中，玩家或者他们的 Bot 都会为自己的蛇选择一个移动方向，游戏引擎会在地图上更新位置并检查是否发生碰撞。

If a snake hits a wall or hits another snake, it dies and the other side wins; if both snakes die in the same round, the game is a draw.  
如果一条蛇撞到墙或者撞到另一条蛇，它就会死亡，另一方获胜；如果两条蛇在同一回合同时死亡，这局对战就是平局。

---

**操作：让一局对战自动跑完**

Now I let the game run automatically to the end of this match.  
现在我让这一局对战自动运行到结束。

When the game is over, the **ResultBoard** component shows the final result, including who wins and who loses.  
当对战结束时，**ResultBoard** 组件会显示最终结果，包括哪一方获胜、哪一方失败。

This demonstrates the correctness of the game rules and the integration between the frontend and backend.  
这说明游戏规则的实现是正确的，前后端之间的集成也是正常工作的。

---

### 2.4 排行榜 Ranklist（页面：`RanklistindexView`）（约 5:20–6:00）

**切换到 `RanklistindexView`**

Now I switch to the **Ranklist** page, which implements the **rating and matchmaking** part of my objectives in AT2.  
现在我切换到 **排行榜** 页面，它实现了 AT2 目标中的 **评分和匹配** 部分。

Here we can see a list of users, usually sorted by their rating or number of wins, and for each user we display basic information such as username, rating, and the number of games played.  
在这里我们可以看到用户列表，通常会按评分或者胜场数量排序；对于每个用户，会展示用户名、评分和对局场次等基本信息。

- `rating (评分)`  

The ranking is updated based on match results stored in the database after each game, providing evidence that the **rating algorithm** and data persistence are working.  
排行榜会根据每一局保存在数据库里的对战结果进行更新，说明 **评分算法** 和数据持久化都在正常工作。

This feature helps users quickly understand their overall performance compared to other players, and supports the skill‑based competitive environment described in AT1.  
这个功能可以帮助用户快速了解自己相对于其他玩家的整体表现，也支撑了 AT1 中提出的基于技能的竞技环境。

---

### 2.5 对战记录 + 回放 Record（页面：`RecordindexView`）（约 6:00–6:40）

**切换到 `RecordindexView`**

Next, I will show the **match records and replay** feature, which corresponds to the **replay system** and data design discussed in AT2.  
接下来我演示一下 **对战记录和回放** 功能，它对应 AT2 中讨论的 **回放系统** 和数据设计。

On this page, we list the history of finished matches, including the players, the result, and the time of each game.  
在这个页面，我们列出了历史上的对战记录，包括双方玩家、结果以及对战时间。

- `history of matches (历史对战记录)`

---

**操作：选择一条记录，点击“回放”**

If I click on one record and start a replay, the frontend loads the stored game steps from the backend.  
如果我点击其中一条记录并开始回放，前端会从后端加载这局游戏保存下来的每一步动作。

Then the replay component replays the game step by step on the same game board.  
然后，回放组件会在同样的棋盘上，把这局对战一回合一回合地重新演示出来。

This is done by storing the sequence of moves in the database and re‑applying them on the frontend, which evidences that the **data model and replay requirements** from AT2 have been implemented.  
这是通过在数据库中保存每一步的动作序列，然后在前端重新执行这些动作来实现的，说明 AT2 中提出的 **数据模型和回放需求** 已经落地。

This feature shows that we not only support real‑time battles, but also persistent storage and analysis of past games, helping users to review and improve their strategies.  
这个功能说明系统不仅支持实时对战，还支持对过去对战的持久化存储和复盘分析，帮助用户复盘和改进自己的策略。

---

### 2.6 Bot 管理 User Bot（页面：`UserBotindexView`）（约 6:40–7:20）

**切换到 `UserBotindexView`**

Now I go to the **User Bot** page, where each user can manage their own bots.  
现在我切换到 **User Bot** 页面，在这里每个用户都可以管理自己的机器人 Bot。

On this page, the user can create a new bot, edit the bot code, or delete a bot.  
在这个页面，用户可以创建新的 Bot，编辑 Bot 的代码，或者删除一个 Bot。

Each bot is a small piece of code that returns the next move direction based on the current game state, which realises the **AI scripting and sandbox** requirements defined in AT2.  
每个 Bot 是一小段代码，根据当前的游戏状态来返回下一步的移动方向，实现了 AT2 中提出的 **AI 脚本和沙箱** 相关需求。

- `strategy (策略)`  

---

**操作：添加一个简单 Bot**

Here I add a simple bot which always moves in one direction or follows a fixed strategy.  
这里我添加一个简单的 Bot，它总是朝一个方向移动，或者按照一个固定策略走。

After saving, the bot is stored in the database and can be selected in future matches, showing that bot data is safely persisted.  
保存之后，这个 Bot 会被存储到数据库中，并且可以在之后的对战中被选择使用，说明 Bot 数据已经被安全持久化。

---

**操作：稍微修改 Bot 代码并保存**

Users can also edit the bot code to implement more complex strategies.  
用户还可以编辑 Bot 的代码，实现更复杂的策略。

This feature shows how the system supports **user‑defined strategies** and interacts with the separate bot running system in the backend, which was a key challenge around **secure code execution** highlighted in AT1 and AT2.  
这个功能展示了系统如何支持 **用户自定义策略**，并且与后端独立的 Bot 运行系统进行交互，而安全地执行这些用户代码正是 AT1 和 AT2 中强调的 **安全代码执行** 技术难点之一。

---

### 2.7 用户信息 Userinfo（页面：`UserinfoView`，可选）（约 7:20–7:40）

Finally, on the **Userinfo** page, the user can see their basic profile and statistics, such as number of matches and win rate.  
最后，在 **Userinfo** 页面，用户可以看到自己的基本信息和统计数据，比如对战场次和胜率。

This helps users understand their long‑term progress on the platform.  
这可以帮助用户了解自己在这个平台上的长期进步情况。

Up to this point, I have demonstrated the main user flows required by the assignment.  
到目前为止，我已经演示了本次作业要求的主要用户流程。

---

## Section 3 – Code Walkthrough（约 7:30–12:30）

> 这里的“当前 IDE 状态”你录制时要配合：先展示项目结构，再按顺序打开文件。

---

### 3.1 整体架构简述（IDE：项目根目录）（约 7:30–8:00）

**IDE：展示项目根目录结构 `backendcloud`、`web`、`botrunningsystem` 等**

Now I will walk through some key parts of the code to explain the architecture of the system and how it realises the **system design** described in my AT2 challenge definition.  
现在我会带大家走读几段关键代码，解释一下系统的整体架构，以及它是如何落地 AT2 中的 **系统设计** 的。

The project is divided into three main modules: the **frontend** built with Vue, the **backend** built with Spring Boot, and a separate **bot running system**, following a layered client‑server architecture.  
整个项目主要分为三个模块：使用 Vue 的 **前端**、使用 Spring Boot 的 **后端**，以及独立的 **Bot 运行系统**，采用分层的客户端–服务器架构。

The frontend handles the user interface and sends REST API and WebSocket requests to the backend.  
前端负责用户界面，并向后端发送 REST API 请求和 WebSocket 实时消息。

The backend provides REST APIs, handles authentication, game logic, rating updates, and database access through a service and data access layer.  
后端通过业务层和数据访问层提供 REST 接口，处理身份认证、游戏逻辑、评分更新以及数据库访问。

The bot running system is responsible for compiling and running user bot code safely and communicating with the game server, reflecting the **secure sandbox** component discussed in AT2.  
Bot 运行系统负责安全地编译和运行用户的 Bot 代码，并与游戏服务器通信，对应 AT2 中提出的 **安全沙箱** 组件。

Overall, the codebase contains several hundred lines of Java and JavaScript code across these modules, with clear separation between UI, business logic, and data access.  
整体来看，代码库在这几个模块中包含了数百行 Java 和 JavaScript 代码，并在界面层、业务逻辑层和数据访问层之间保持了清晰的职责分离。

- `architecture (架构)`  
- `module (模块)`  

---

### 3.2 用户认证与安全：JWT Filter + User Details Service  
（IDE：打开 `JwtAuthenticationTokenFilter.java` 和 `UserDetailServiceImpl.java`）（约 8:00–9:20）

**IDE：定位到 `JwtAuthenticationTokenFilter` 的 `doFilterInternal` 或核心方法**

First, I will introduce the **authentication and security** part, which implements the secure **JWT‑based** login system defined as an objective in AT2.  
首先，我介绍一下系统里的 **身份认证和安全** 部分，它实现了 AT2 目标中提出的基于 **JWT** 的安全登录系统。

This class is `JwtAuthenticationTokenFilter`.  
这个类是 `JwtAuthenticationTokenFilter`。

For every incoming HTTP request, this filter checks if there is a **JWT token** in the header.  
对于每一个进入系统的 HTTP 请求，这个过滤器都会检查请求头里是否带有 **JWT token**。

If a token is present, the filter parses it, validates it, and then loads the user details from the database.  
如果 token 存在，过滤器会解析并验证这个 token，然后从数据库中加载用户的详细信息。

After that, it creates an authentication object and puts it into the Spring Security context so that other parts of the application know who the current user is and what permissions they have.  
之后，它会创建一个认证对象，并把它放到 Spring Security 的安全上下文中，这样应用的其他部分在处理请求时始终知道当前用户是谁、拥有哪些权限。

- `authorization (授权，访问控制)`  
- `security context (安全上下文)`  

This illustrates my understanding of **authorization (授权)** and **security context (安全上下文)** in Spring Security.  
这也体现了我对 Spring Security 中 **授权 (authorization)** 和 **安全上下文 (security context)** 机制的理解。

---

**IDE：切换到 `UserDetailServiceImpl` 的 `loadUserByUsername`**

This class is `UserDetailServiceImpl`, which implements the standard `UserDetailsService` interface from Spring Security.  
这个类是 `UserDetailServiceImpl`，它实现了 Spring Security 提供的标准接口 `UserDetailsService`。

When a user logs in with username and password, Spring Security calls this method to load the user from the database, including their encoded password and roles.  
当用户用用户名和密码登录时，Spring Security 会调用这个方法，从数据库中加载对应的用户信息，包括加密后的密码和角色。

Together, `JwtAuthenticationTokenFilter` and `UserDetailServiceImpl` show a complete path from **login request → token validation → loading user details → setting security context**, which evidences my understanding and ownership of the security‑related code.  
`JwtAuthenticationTokenFilter` 和 `UserDetailServiceImpl` 配合起来，完整实现了从 **登录请求 → token 校验 → 加载用户信息 → 设置安全上下文** 的完整路径，体现了我对这部分安全相关代码的理解和掌控。

---

### 3.3 对战逻辑 / 游戏状态管理  
（IDE：打开 `botrunningsystem` 或后端的 `Consumer.java` 和 `Bot.java` 等）（约 9:20–10:40）

**IDE：打开 `Consumer.java`，展示处理每一回合游戏逻辑的方法**

Next, I will explain the **game logic and state management**, which is the centre of the real‑time engine described in my AT2 design section.  
接下来，我解释一下 **对战逻辑和游戏状态管理**，它是 AT2 设计部分中描述的实时游戏引擎的核心。

This class is `Consumer`. It receives game commands, updates the game state step by step, and sends results back to all connected clients.  
这个类叫做 `Consumer`，它负责接收游戏指令，逐步更新游戏状态，并把结果发送给所有连接的客户端。

Inside this class, we keep the current map, the positions of all snakes, and the directions chosen by each player or bot. In each round, the consumer reads the next move from both sides, updates the positions, checks for collisions, and decides who is dead or alive.  
在这个类里面，我们保存了当前的地图、每条蛇的位置，以及每个玩家或者 Bot 选择的移动方向；在每一回合中，`Consumer` 会读取双方的下一步动作，更新位置，检查是否发生碰撞，并决定哪条蛇存活、哪条蛇死亡。

If a snake hits a wall or hits another snake, it is marked as dead and the game may end; this directly implements the game rules demonstrated earlier in the PK page.  
如果一条蛇撞墙或者撞到另一条蛇，就会被标记为死亡，这局游戏可能就结束了，对应前面 PK 页面里演示的游戏规则。

Because multiple matches can run at the same time, we also need to consider **concurrency (并发)** and carefully manage shared data structures so that one match does not affect another.  
因为系统里可以同时运行多局对战，所以我们还需要考虑 **并发 (concurrency)**，小心地管理共享数据结构，避免一局对战影响到另一局。

---

### 3.4 Bot 运行系统与用户自定义代码  
（IDE：`botrunningsystem` 下的 `Bot.java` 等）（约 10:40–11:40）

**IDE：打开 `Bot.java` 或相关类，展示如何封装用户 Bot**

Now let’s look at the **bot running system** and how we support user‑defined bot code, which was one of the main technical challenges identified in AT1 and AT2.  
现在我们来看一下 **Bot 运行系统**，以及系统如何支持用户自定义的 Bot 代码，这也是 AT1 和 AT2 中识别出的主要技术挑战之一。

In this part, we define a `Bot` class that holds the code submitted by the user and some metadata like language and user id.  
在这里，我们定义了一个 `Bot` 类，用来保存用户提交的代码，以及一些元数据，比如语言类型、用户 ID 等。

When a match involves a bot, the game engine will send the current game state to the bot running system.  
当一局对战中有 Bot 参与时，游戏引擎会把当前的游戏状态发送给 Bot 运行系统。

The bot running system compiles or interprets the user’s code, executes it in a **sandbox** style environment, and reads the move direction returned by the bot.  
Bot 运行系统会编译或者解释用户的代码，在一个类似沙箱的环境中执行它，然后读取 Bot 返回的移动方向。

- `sandbox (沙箱，受限制的运行环境)`  

We also need to limit the running time and resources of the bot to prevent infinite loops or attacks, which follows the **risk analysis and mitigation** strategies for security that I discussed in AT2.  
我们还需要限制 Bot 的运行时间和资源，防止出现死循环或者恶意攻击，这也对应了 AT2 中关于安全性的 **风险分析和缓解策略**。

Finally, the chosen direction is sent back to the game consumer, and the game continues.  
最后，Bot 选择的方向会被发送回游戏的 Consumer，游戏就这样一回合一回合继续进行。

This design allows users to write their own strategies, while keeping the main game server safe and stable, and demonstrates my understanding of **secure code execution** and separation of concerns.  
这种设计既允许用户编写自己的策略，又能保证主游戏服务器的安全和稳定，也展示了我对 **安全代码执行** 和“职责分离”设计思想的理解。

---

### 3.5（可选）数据库交互 / 记录存储（约 11:40–12:10）

**IDE：简单展示一个记录或用户的 Mapper / Repository 接口**

Finally, I briefly show the **data access layer**, which implements the data model and persistence design from AT2.  
最后，我简单展示一下 **数据访问层**，它实现了 AT2 中的数据模型和持久化设计。

Here we use mapper or repository interfaces to store users, match records, rankings, and bot scripts in the database.  
在这里，我们通过 Mapper 或 Repository 接口，把用户信息、对战记录、排行榜数据和 Bot 脚本存储到数据库中。

Each time a match finishes, the backend creates a record entity, saves the result, updates the ratings, and also saves the steps for replay.  
每当一局对战结束，后端就会创建一条记录实体，保存本局的结果，更新评分，并同时保存整局对战的步骤数据，以便之后回放使用。

This separation of concerns makes the code easier to maintain and extend, and shows how I applied standard **layered architecture** ideas from software engineering theory.  
这种按职责分层的设计，让代码更容易维护和扩展，也展示了我是如何应用软件工程理论中的 **分层架构** 思想的。

---

## Section 4 – Conclusion（约 12:30–14:00）

### 4.1 回顾项目目标与达成情况

To conclude, this project set out – in AT1 and AT2 – to build a **real‑time competitive snake AI platform** with secure authentication, a low‑latency multiplayer game engine, AI scripting support, and a rating and replay system.  
最后总结一下，按照 AT1 和 AT2 中的设定，本项目的目标是构建一个支持安全认证、低延迟多人对战、AI 脚本以及评分和回放系统的 **实时贪吃蛇 AI 对战平台**。

In the current implementation, the main goals have been **largely achieved**: users can register and log in via JWT‑based security, play real‑time matches over WebSocket, manage their bots, and review their performance through rankings and match records with replay.  
在目前的实现中，这些主要目标已经 **基本达成**：用户可以通过基于 JWT 的安全机制注册和登录，通过 WebSocket 进行实时对战，管理自己的 Bot，并通过排行榜和带回放的对战记录回顾自己的表现。

---

### 4.2 主要成就与技术点

From a technical point of view, there are three main achievements.  
从技术角度来看，可以总结出三方面的主要成果：

First, I implemented **JWT‑based authentication and authorization** on top of Spring Security, so that every request is associated with a verified user in the security context.  
第一，我在 Spring Security 的基础上实现了基于 **JWT 的身份认证和授权**，保证了每个请求都和安全上下文中的已验证用户关联。

Second, I built a **real‑time snake game engine** using WebSocket and a central `Consumer` to manage game state, collisions, and concurrent matches, which directly addresses the networking and concurrency challenges discussed in AT2.  
第二，我使用 WebSocket 和中心 `Consumer` 构建了一个 **实时贪吃蛇游戏引擎**，负责管理游戏状态、碰撞检测和多局并发对战，直接回应了 AT2 中提出的网络和并发挑战。

Third, I developed a **bot running system and replay‑aware data model**: users can write and manage bot scripts, which are executed in a sandbox‑style environment, and all game steps are stored for ranking updates and replay.  
第三，我实现了一个 **Bot 运行系统和支持回放的数据模型**：用户可以编写和管理 Bot 脚本，这些脚本在类似沙箱的环境中运行，同时系统会保存整局对战步骤，用于评分更新和对战回放。

---

### 4.3 不足与未来工作

There are still several areas where the implementation is **simpler than the original AT2 plan**.  
当然，和 AT2 中的原始计划相比，目前的实现还有一些 **简化和不足**。

For example, the current rating system is inspired by ELO but does not yet implement a full relaxation‑based matchmaking algorithm, the bot sandbox supports a narrower set of execution options than originally designed, and the user interface could be further polished.  
例如，当前的评分系统借鉴了 ELO 思路，但尚未完全实现计划中的放宽匹配算法；Bot 沙箱在执行环境方面也比原设计略为收敛，界面和错误提示仍有提高空间。

In future work, I would like to refine the **rating and matchmaking** logic, explore support for more sophisticated bot strategies and possibly additional languages, and add richer analytics tools around replays and player behaviour.  
在后续工作中，我希望进一步完善 **评分和匹配** 逻辑，探索支持更复杂的 Bot 策略甚至更多语言，并在回放和玩家行为上增加更丰富的分析工具。

---

### 4.4 学习收获

Through this project, I practiced the full software engineering workflow from AT1 and AT2 into AT3: defining the problem and objectives, designing the architecture and data model, implementing features, and testing them through real use.  
通过这个项目，我把 AT1 和 AT2 中的设想真正落地到 AT3，从问题和目标定义，到架构和数据模型设计，再到功能实现和在真实使用中的测试，完整走了一遍软件工程流程。

I learned how to combine a **Vue frontend, Spring Boot backend, and a separate bot service** into one coherent system, while balancing real‑time performance, security, and user experience.  
我学会了如何把 **Vue 前端、Spring Boot 后端和独立的 Bot 服务** 组合成一个统一的系统，并在实现中平衡实时性能、安全性和用户体验。

Overall, this project has strengthened my confidence in tackling **full‑stack, real‑time, and security‑sensitive** web applications in the future.  
总体来说，这个项目加强了我对未来继续开发 **全栈、实时、注重安全性** 的 Web 应用的信心。

---

## 时间轴 + 页面切换总表（导演脚本版）

你可以把下面当成录制时的提纲：

- **0:00–0:30**：摄像头 + 登录页 / 首页  
  - 说姓名、课程、项目标题（英文为主，简单一句中文补充）。  
- **0:30–1:30**：仍在登录页或首页  
  - 按 Section 1 稿子讲项目要解决的问题、总体目标，以及实际完成度，点出“实时对战 + AI 脚本 + 评分和回放”这几个关键词。  

- **1:30–3:00**：`UserAccountRegisterView` + `UserAccountLoginView`  
  - 演示注册错误输入 → 正确注册 → 错误密码登录 → 正确登录。  
  - 强调输入校验、错误提示、JWT 认证，以及这些如何对应 AT2 中的“用户管理 + 安全需求”。  

- **3:00–3:40**：主界面 + `NavBar`  
  - 指着菜单介绍整体结构（PK / Ranklist / Record / Bot / Userinfo），顺带说一下它们分别对应 AT2 里的功能需求。  

- **3:40–5:20**：`PKindexView` + `MatchGround` + `ResultBoard`  
  - 开始匹配，解释规则，让一局对战跑完，讲胜负判定和并发多局的支持，点出 WebSocket 实时更新。  

- **5:20–6:00**：`RanklistindexView`  
  - 展示排行榜字段，说明排序依据来自数据库中的对战结果，简单提到评分逻辑受 ELO 启发。  

- **6:00–6:40**：`RecordindexView`  
  - 选一条记录，播放回放，讲“保存步骤 → 前端重放”的思路，并点一下这是 AT2 中回放需求的实现。  

- **6:40–7:20**：`UserBotindexView`  
  - 新建 Bot、改一点代码、保存。强调用户自定义策略、Bot 运行系统以及安全沙箱和资源限制。  

- **7:20–7:30(可选)**：`UserinfoView`  
  - 简单展示个人数据。  

- **7:30–8:00**：IDE 项目根目录  
  - 讲整体架构：前端 / 后端 / Bot 运行系统 / 数据库。  

- **8:00–9:20**：`JwtAuthenticationTokenFilter.java` + `UserDetailServiceImpl.java`  
  - 讲请求进来 → 解析 JWT → 加载用户 → 放入 Security Context。  

- **9:20–10:40**：`Consumer.java`（游戏逻辑）  
  - 讲地图、蛇位置、每一步更新、碰撞检测、并发考虑。  

- **10:40–11:40**：`Bot.java` 等 Bot 运行代码  
  - 讲用户代码 → 沙箱执行 → 返回方向 → 回到游戏。  

- **11:40–12:10**：一个 Record / User 的 Mapper 或 Repository  
  - 讲怎么存用户、对战记录和回放数据。  

- **12:10–14:00**：任意你喜欢的稳定画面（首页 / 排行榜 / IDE 总览）  
  - 按 Section 4 的稿子总结目标达成度、主要成就、未来工作和个人收获。  

